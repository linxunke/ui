package com.ztzh.ui.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.im4java.core.IM4JavaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ztzh.ui.bo.IconMaterialBo;
import com.ztzh.ui.bo.IconUrlBo;
import com.ztzh.ui.bo.IconUrlResultBo;
import com.ztzh.ui.bo.MaterialAndTypeInfoBo;
import com.ztzh.ui.bo.ThreeRecentUrlResultBo;
import com.ztzh.ui.dao.CanvasInfoDomainMapper;
import com.ztzh.ui.dao.MaterialHistoryCollectionDomainMapper;
import com.ztzh.ui.dao.MaterialInfoDomainMapper;
import com.ztzh.ui.dao.MaterialTypeInfoDomainMapper;
import com.ztzh.ui.po.MaterialInfoDomain;
import com.ztzh.ui.po.MaterialTypeInfoDomain;
import com.ztzh.ui.service.MaterialInfoService;
import com.ztzh.ui.utils.FTPUtil;
import com.ztzh.ui.utils.GetSYSTime;
import com.ztzh.ui.utils.ImageMagickUtil;
import com.ztzh.ui.vo.ResponseVo;

@Service
public class MaterialInfoServiceImpl implements MaterialInfoService{
	Logger logger = LoggerFactory.getLogger(MaterialInfoServiceImpl.class);
	@Autowired
	MaterialInfoDomainMapper materialInfoDomainMapper;
	
	@Autowired
	CanvasInfoDomainMapper canvasInfoDomainMapper;
	
	@Autowired
	MaterialTypeInfoDomainMapper materialTypeInfoDomainMapper;
	
	@Autowired
	FTPUtil ftpUtil;
	
	@Autowired
	MaterialHistoryCollectionDomainMapper materialHistoryCollectionDomainMapper;
	
	@Autowired
	ImageMagickUtil imageMagickUtil;
	
	@Value("${material.catch.png.url}")
	private String catchPngUrl;
	
	@Value("${web.upload-path}")
	private String resourceFTPUrl;
	
	@Value("{material.catch.resource.url}")
	private String catchResourceUrl;
	
	@Override
	public List<IconUrlResultBo> getDisplayUrlByThumbnailUrl(List<String> thumbnailUrls) {
	    List<IconMaterialBo> iconMaterialBos = materialInfoDomainMapper.getIconMaterialBoByThumbnailUrls(thumbnailUrls);
	    List<String> materialInfoIdList = new ArrayList<String>();
	    for(IconMaterialBo iconMaterialBo:iconMaterialBos) {
	    	materialInfoIdList.add(iconMaterialBo.getMaterialInfoId());
	    }
	    List<IconUrlBo> iconUrlBoList = canvasInfoDomainMapper.getMaterialInfosOfCanvasByMaterialInfoId(materialInfoIdList);
	    List<IconUrlResultBo> iconUrlResultBoList = new ArrayList<IconUrlResultBo>();
	    for(int i=0;i<iconUrlBoList.size();i++) {
	    	IconUrlResultBo iconUrlResultBo = new IconUrlResultBo();
	    	String canvasId = iconUrlBoList.get(i).getCanvasId();
	    	List<String> iconUrls = materialInfoDomainMapper.getIconUrlsByCanvasId(canvasId);
	    	iconUrlResultBo.setCanvasId(canvasId);
	    	iconUrlResultBo.setPngUrls(iconUrls);
	    	iconUrlResultBoList.add(iconUrlResultBo);
	    }
		return iconUrlResultBoList;
	}
	
	public List<ThreeRecentUrlResultBo> getThreeRecentMaterial(){
		String materialType;
		String childType;
		List<ThreeRecentUrlResultBo> list = materialInfoDomainMapper.getThreeRecentMaterial();
		for(int i=0; i<3; i++){
			childType = materialInfoDomainMapper.selectTypeNameByChildCode(list.get(i).getChildCode());
			materialType= list.get(i).getMaterialType();
			list.get(i).setMaterialType(materialType+"—"+childType);
		}
		return list;
	}
	public int selectIconCount(){
		return materialInfoDomainMapper.selectIconCount();
		
	}
	public int selectDrawingCount(){
		return materialInfoDomainMapper.selectDrawingCount();
		
	}

	@Override
	public List<MaterialInfoDomain> getMaterialListByCanvasId(Long canvasId) {
		return materialInfoDomainMapper.selectByCanvasId(canvasId);
	}

	@Override
	public int getMaterialNumOfCanvasByCanvasId(long canvasId) {
		return materialInfoDomainMapper.getMaterialNumOfCanvasByCanvasId(canvasId);
	}
	
	@Transactional
	public ResponseVo updateMaterialsInfo(Long materialInfoId, String imageLabel, Long canvasId, String imageName, String typeArray) throws Exception {
		logger.info("获取源文件基本信息");
		MaterialInfoDomain materialInfo = materialInfoDomainMapper.selectByPrimaryKey(materialInfoId);
		logger.info("删除源文件信息");
		int count = materialInfoDomainMapper.deleteByPrimaryKey(materialInfoId);
		if(count>1) {
			throw new Exception("删除源文件数据失败");
		}
		materialInfo.setMaterialDescription(imageLabel);
		materialInfo.setCanvasInfoIdPrivate(canvasId);
		materialInfo.setCanvasInfoIdPublic(canvasId);
		materialInfo.setMaterialName(imageName);
		materialInfo.setUploadTime(new Date());
		materialInfoDomainMapper.insert(materialInfo);
		logger.info("删除所属类型信息");
		materialTypeInfoDomainMapper.deleteByMaterialInfoId(materialInfoId);
		JSONArray typesJsonList = JSONArray.parseArray(typeArray);
		List<MaterialTypeInfoDomain> typesList = new ArrayList<MaterialTypeInfoDomain>();
		for (int i = 0; i < typesJsonList.size(); i++) {
			JSONArray typeJson = (JSONArray) typesJsonList.get(i);
			Map<String, String> typeMap = new HashMap<String, String>(); // map用来存放类别、细分、风格信息
			typeMap.put("materialTypeCodeParent", typeJson.get(0).toString());
			typeMap.put("materialTypeCodeChild", typeJson.get(1).toString());
			typeMap.put("materialStyleCode", typeJson.get(2).toString());
			String json = JSONObject.toJSONString(typeMap);
			MaterialTypeInfoDomain materialTypeInfoDomain = JSONObject.parseObject(json, MaterialTypeInfoDomain.class);
			materialTypeInfoDomain.setMaterailInfoId(materialInfoId);
			materialTypeInfoDomain.setCreateTime(new Date());
			typesList.add(materialTypeInfoDomain);
		}
		int materialTypeCount = 0;
		try {
			materialTypeCount = materialTypeInfoDomainMapper.insertBybatch(typesList);
		}catch(Exception e) {
			e.printStackTrace();
			throw new Exception("删除源文件数据失败");
		}
				
		logger.info("成功插入{}条源文件类型信息",materialTypeCount);
		ResponseVo responseVo = new ResponseVo();
		responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
		responseVo.setMessage("更新文件信息成功");
		return responseVo;
	}

	@Override
	@Transactional
	public ResponseVo deleteMaterialsById(String materialIdsJson) {
		ResponseVo responseVo = new ResponseVo();
		logger.info("准备删除源文件信息");
		List<Long> materialIds = new ArrayList<Long>();
		JSONArray materialIdsJsonList = JSONArray.parseArray(materialIdsJson);
		for (int i = 0; i < materialIdsJsonList.size(); i++) {
			String typeJson = materialIdsJsonList.get(i).toString();
			MaterialTypeInfoDomain materialTypeInfoDomain = JSONObject.parseObject(typeJson, MaterialTypeInfoDomain.class);
			materialIds.add(materialTypeInfoDomain.getId());
		}
		logger.info("删除有关该文件所有磁盘中的文件");
		logger.info("获取所有磁盘地址");
		List<MaterialInfoDomain> materialInfoDomainList = materialInfoDomainMapper.queryByIds(materialIds);
		List<String> materialUrlList = new ArrayList<String>();
		List<String> thumbnailUrlList = new ArrayList<String>();
		List<String> pngUrlList = new ArrayList<String>();
		for(MaterialInfoDomain materialInfoDomain:materialInfoDomainList) {
			materialUrlList.add(materialInfoDomain.getMaterialUrl());
			thumbnailUrlList.add(materialInfoDomain.getThumbnailUrl());
			pngUrlList.add(materialInfoDomain.getPngUrl());
		}
		deleteMaterialsInFTP(materialUrlList, thumbnailUrlList, pngUrlList);
		logger.info("删除完ftp中有关文件");
		int countMaterials = materialInfoDomainMapper.deleteByIds(materialIds);
		int countMaterialTypes = materialTypeInfoDomainMapper.deleteByMaterialInfoIds(materialIds);
		int countHistory = materialHistoryCollectionDomainMapper.deleteByMaterialInfoIds(materialIds);
		logger.info("成功删除{}个文件信息,{}条文件类型信息,{}条关注信息",countMaterials,countMaterialTypes,countHistory);
		responseVo.setMessage("批量删除文件成功");
		responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
		return responseVo;
	}	
	private void deleteMaterialsInFTP(List<String> materialUrlList, List<String> thumbnailUrlList, List<String> pngUrlList) {
		//有可能删除失败，后面需要一张事务表管理未删除掉的ftp文件
		ftpUtil.deleteFtpFile(materialUrlList);
		ftpUtil.deleteFtpFile(thumbnailUrlList);
		ftpUtil.deleteFtpFile(pngUrlList);
	}

	@Override
	public MaterialAndTypeInfoBo getMaterialDetailInfoById(Long materialId) {
		MaterialAndTypeInfoBo materialAndTypeInfoBo = materialInfoDomainMapper.selectMaterialDetailsInfo(materialId);
		logger.info(materialAndTypeInfoBo.toString());
		return materialAndTypeInfoBo;
	}

	@Override
	public MaterialInfoDomain getMaterialInfoById(Long materialId) {
		return materialInfoDomainMapper.selectByPrimaryKey(materialId);
	}

	@Override
	public Map<String, String> getImageUrlAndName(MaterialInfoDomain materialInfoDomain,
			String imageType, boolean isIcon, Integer iconSize) {
		Map<String,String> imageMap = new HashMap<String, String>();
		if("original".equalsIgnoreCase(imageType)){
			/*下载原文件(ai、psd)，将缓存文件路径返回*/
			String oldPath = resourceFTPUrl + materialInfoDomain.getMaterialUrl();
			oldPath = oldPath.replaceAll("//", "/");
			String imageTempName = UUID.randomUUID().toString().replaceAll("-", "") + "." + materialInfoDomain.getMaterialType();
			String newPath = catchPngUrl + imageTempName;
			newPath = newPath.replaceAll("\\\\", "/");
			String imageName = materialInfoDomain.getMaterialName() + "." + materialInfoDomain.getMaterialType();
			boolean copyResult = copy(oldPath, newPath);
			if(copyResult){
				imageMap.put("imageUrl", "/images/"+imageTempName);
				imageMap.put("imageName", imageName);
			}
			return imageMap;
		}
		if ("png".equalsIgnoreCase(imageType)) {
			if(isIcon){
			    /*将png图片缓存至缓存文件夹，并转换大小，返回缓存的图片路径*/
				String oldImgPath = resourceFTPUrl + materialInfoDomain.getPngUrl();
				oldImgPath = oldImgPath.replaceAll("//", "/");
				String imageTempName = UUID.randomUUID().toString().replaceAll("-", "") + ".png";
				String newPath = catchPngUrl + imageTempName;
				newPath = newPath.replaceAll("\\\\", "/");
				String imageName = materialInfoDomain.getMaterialName() + ".png";
				boolean zoomResult = imageMagickUtil.zoomImage(oldImgPath, newPath, iconSize, iconSize);
				if(zoomResult){
					imageMap.put("imageUrl", "/images/"+imageTempName);
					imageMap.put("imageName", imageName);
				}
				
				return imageMap;
			}else {
				String oldPath = resourceFTPUrl + materialInfoDomain.getMaterialUrl();
				oldPath = oldPath.replaceAll("//", "/");
				String imageTempName = UUID.randomUUID().toString().replaceAll("-", "")+ ".png";
				String newPath = catchPngUrl + imageTempName;
				newPath = newPath.replaceAll("\\\\", "/");
				String imageName = materialInfoDomain.getMaterialName()+".png";
				boolean copyResult = copy(oldPath, newPath);
				if(copyResult){
					imageMap.put("imageUrl", "/images/"+imageTempName);
					imageMap.put("imageName", imageName);
				}
				return imageMap;
			}
		}
		if("svg".equalsIgnoreCase(imageType)){
			/*下载svg图片，返回缓存文件的路径*/
			String oldImgPath = resourceFTPUrl + materialInfoDomain.getPngUrl();
			oldImgPath = oldImgPath.replaceAll("//", "/");
			String imageTempName = UUID.randomUUID().toString().replaceAll("-", "") + ".svg";
			String newPath = catchPngUrl + imageTempName;
			newPath = newPath.replaceAll("\\\\", "/");
			String imageName = materialInfoDomain.getMaterialName() + ".svg";
			String[] imgPaths = {oldImgPath};
			try {
				boolean convertResult = imageMagickUtil.convertType(imgPaths, newPath);
				if(convertResult){
					imageMap.put("imageUrl", "/images/"+imageTempName);
					imageMap.put("imageName", imageName);
					return imageMap;
				}
			} catch (IOException | InterruptedException | IM4JavaException e) {
				logger.info("转换svg文件失败");
				e.printStackTrace();
			}
		}
		return imageMap;
	}
	
	
	public static boolean copy(String src, String dst) {
		boolean result = true;
		// 提供需要读入和写入的文件
		File fileIN = new File(src);
		File fileOUT = new File(dst);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			// 创建相应的节点流，将文件对象作为形参传递给节点流的构造器
			FileInputStream fis = new FileInputStream(fileIN);
			FileOutputStream fos = new FileOutputStream(fileOUT);
			// 创建相应的缓冲流，将节点流对象作为形参传递给缓冲流的构造器
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(fos);

			// 具体的文件复制操作
			byte[] b = new byte[65536]; // 把从输入文件读取到的数据存入该数组
			int len; // 记录每次读取数据并存入数组中后的返回值，代表读取到的字节数，最大值为b.length；当输入文件被读取完后返回-1
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
				bos.flush();
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
			return result;
		} finally {
			// 关闭流，遵循先开后关原则(这里只需要关闭缓冲流即可)
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
