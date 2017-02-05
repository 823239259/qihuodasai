package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;

@Entity
@Table(name = "crawler_wallstreetn_live_content")
public class CrawlerWallstreetnLiveContent extends BaseEntity{

	private static final long serialVersionUID = 1L;
	/**
	 * 实时数据id
	 */
	private String liveId;
	/**
	 * 实时数据html内容
	 */
	private String liveContentHtml;
	/**
	 * 实时数据文本内容
	 */
	private String liveContentText;
	/**
	 * 实时数据图片
	 */
	private String liveContentImage;
	/**
	 * 创建时间
	 */
	private Long liveContentCreatetime;
	/**
	 * 修改时间
	 */
	private Long liveContentUpdatetime;
	private String imageCount;
	private String image;
	private String videoCount;
	private String video;
	private String viewCount;
	private String shareCount;
	private String commentCount;
	private String data;
	private String sourceName;
	private String sourceUrl;
	private String userId;
	private String categorySet;
	private String hasMore;
	private String detailPost;
	private String contentExtra;
	private String contentFollowup;
	private String contentAnalysis;
	private String moreText;
	private String moreImgs;
	private String imageUrls;
	private String nodeColor;
	private String nodeFormat;
	/**
	 * 
	 * @return
	 */
	private String text;
	public String getLiveId() {
		return liveId;
	}
	public void setLiveId(String liveId) {
		this.liveId = liveId;
	}
	public String getLiveContentHtml() {
		return liveContentHtml;
	}
	public void setLiveContentHtml(String liveContentHtml) {
		this.liveContentHtml = liveContentHtml;
	}
	public String getLiveContentText() {
		return liveContentText;
	}
	public void setLiveContentText(String liveContentText) {
		this.liveContentText = liveContentText;
	}
	public String getLiveContentImage() {
		return liveContentImage;
	}
	public void setLiveContentImage(String liveContentImage) {
		this.liveContentImage = liveContentImage;
	}
	public Long getLiveContentCreatetime() {
		return liveContentCreatetime;
	}
	public void setLiveContentCreatetime(Long liveContentCreatetime) {
		this.liveContentCreatetime = liveContentCreatetime;
	}
	public Long getLiveContentUpdatetime() {
		return liveContentUpdatetime;
	}
	public void setLiveContentUpdatetime(Long liveContentUpdatetime) {
		this.liveContentUpdatetime = liveContentUpdatetime;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public CrawlerWallstreetnLiveContent(String liveId, String liveContentHtml, String liveContentText,
			String liveContentImage, Long liveContentCreatetime, Long liveContentUpdatetime, String text) {
		super();
		this.liveId = liveId;
		this.liveContentHtml = liveContentHtml;
		this.liveContentText = liveContentText;
		this.liveContentImage = liveContentImage;
		this.liveContentCreatetime = liveContentCreatetime;
		this.liveContentUpdatetime = liveContentUpdatetime;
		this.text = text;
	}
	
	public String getImageCount() {
		return imageCount;
	}
	public void setImageCount(String imageCount) {
		this.imageCount = imageCount;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getVideoCount() {
		return videoCount;
	}
	public void setVideoCount(String videoCount) {
		this.videoCount = videoCount;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getViewCount() {
		return viewCount;
	}
	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}
	public String getShareCount() {
		return shareCount;
	}
	public void setShareCount(String shareCount) {
		this.shareCount = shareCount;
	}
	public String getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCategorySet() {
		return categorySet;
	}
	public void setCategorySet(String categorySet) {
		this.categorySet = categorySet;
	}
	public String getHasMore() {
		return hasMore;
	}
	public void setHasMore(String hasMore) {
		this.hasMore = hasMore;
	}
	public String getDetailPost() {
		return detailPost;
	}
	public void setDetailPost(String detailPost) {
		this.detailPost = detailPost;
	}
	public String getContentExtra() {
		return contentExtra;
	}
	public void setContentExtra(String contentExtra) {
		this.contentExtra = contentExtra;
	}
	public String getContentFollowup() {
		return contentFollowup;
	}
	public void setContentFollowup(String contentFollowup) {
		this.contentFollowup = contentFollowup;
	}
	public String getContentAnalysis() {
		return contentAnalysis;
	}
	public void setContentAnalysis(String contentAnalysis) {
		this.contentAnalysis = contentAnalysis;
	}
	public String getMoreText() {
		return moreText;
	}
	public void setMoreText(String moreText) {
		this.moreText = moreText;
	}
	public String getMoreImgs() {
		return moreImgs;
	}
	public void setMoreImgs(String moreImgs) {
		this.moreImgs = moreImgs;
	}
	public String getImageUrls() {
		return imageUrls;
	}
	public void setImageUrls(String imageUrls) {
		this.imageUrls = imageUrls;
	}
	
	public String getNodeColor() {
		return nodeColor;
	}
	public void setNodeColor(String nodeColor) {
		this.nodeColor = nodeColor;
	}
	public String getNodeFormat() {
		return nodeFormat;
	}
	public void setNodeFormat(String nodeFormat) {
		this.nodeFormat = nodeFormat;
	}
	public CrawlerWallstreetnLiveContent() {
	}

	
}
