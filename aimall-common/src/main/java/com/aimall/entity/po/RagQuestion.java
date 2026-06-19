package com.aimall.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.aimall.entity.enums.DateTimePatternEnum;
import com.aimall.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * rag闂
 */
public class RagQuestion implements Serializable {


	/**
	 * 鑷ID
	 */
	private Integer questionId;

	/**
	 * 闂
	 */
	private String question;

	/**
	 * 鐩镐技闂
	 */
	private String similarQuestion;

	/**
	 * 绛旀
	 */
	private String answer;

	/**
	 * 鍒涘缓鏃堕棿
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;


	public void setQuestionId(Integer questionId){
		this.questionId = questionId;
	}

	public Integer getQuestionId(){
		return this.questionId;
	}

	public void setQuestion(String question){
		this.question = question;
	}

	public String getQuestion(){
		return this.question;
	}

	public void setSimilarQuestion(String similarQuestion){
		this.similarQuestion = similarQuestion;
	}

	public String getSimilarQuestion(){
		return this.similarQuestion;
	}

	public void setAnswer(String answer){
		this.answer = answer;
	}

	public String getAnswer(){
		return this.answer;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	@Override
	public String toString (){
		return "鑷ID:"+(questionId == null ? "绌? : questionId)+"锛岄棶棰?"+(question == null ? "绌? : question)+"锛岀浉浼奸棶棰?"+(similarQuestion == null ? "绌? : similarQuestion)+"锛岀瓟妗?"+(answer == null ? "绌? : answer)+"锛屽垱寤烘椂闂?"+(createTime == null ? "绌? : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}

