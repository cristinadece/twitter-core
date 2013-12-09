///**
// *  Copyright 2012 Diego Ceccarelli
// *
// *  Licensed under the Apache License, Version 2.0 (the "License");
// *  you may not use this file except in compliance with the License.
// *  You may obtain a copy of the License at
// * 
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// *  Unless required by applicable law or agreed to in writing, software
// *  distributed under the License is distributed on an "AS IS" BASIS,
// *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *  See the License for the specific language governing permissions and
// *  limitations under the License.
// */
//package it.cnr.isti.hpc.twitter.domain.hashtag;
//
//import java.util.List;
//
///**
// * Hashtag.java
// * 
// * @author Diego Ceccarelli, diego.ceccarelli@isti.cnr.it created on 21/mar/2012
// */
//public class Hashtag implements Comparable {
//
//	private static HashtagSegmenter segmenter = HashtagSegmenter
//			.getStandardSegmenter();
//
//	private String hashtag;
//	private String segmentedHashtag;
//	private int frequency;
//	private int contextFrequency;
//	private int inlineFrequency;
//	private int trainingFrequency;
//	private int testFrequency;
//	private boolean isSpam;
//
//	private List<String> relatedHashtags;
//
//	public Hashtag(String hashtag) {
//		isSpam = false;
//		this.hashtag = hashtag;
//
//	}
//
//	@Override
//	public String toString() {
//		return "Hashtag [hashtag=" + hashtag + ", segmentedHashtag="
//				+ segmentedHashtag + ", frequency=" + frequency
//				+ ", contextFrequency=" + contextFrequency
//				+ ", inlineFrequency=" + inlineFrequency
//				+ ", trainingFrequency=" + trainingFrequency
//				+ ", testFrequency=" + testFrequency + ", isSpam=" + isSpam
//				+ "]";
//	}
//
//	public String getHashtag() {
//		return hashtag;
//	}
//
//	public String getSegmentedHashtag() {
//		if (segmentedHashtag == null) {
//			
//			if (segmenter.contains(hashtag)) {
//				this.segmentedHashtag = segmenter.segment(hashtag);
//			}else{
//				this.segmentedHashtag = "";
//			}
//		}
//		return segmentedHashtag;
//	}
//
//	public int getFrequency() {
//		return frequency;
//	}
//
//	public int getContextFrequency() {
//		return contextFrequency;
//	}
//
//	public int getInlineFrequency() {
//		return inlineFrequency;
//	}
//
//	public int getTrainingFrequency() {
//		return trainingFrequency;
//	}
//
//	public int getTestFrequency() {
//		return testFrequency;
//	}
//
//	public boolean isSpam() {
//		return isSpam;
//	}
//
//	public List<String> getRelatedHashtags() {
//		return relatedHashtags;
//	}
//
//	public void setHashtag(String hashtag) {
//		this.hashtag = hashtag;
//	}
//
//	public void setSegmentedHashtag(String segmentedHashtag) {
//		this.segmentedHashtag = segmentedHashtag;
//	}
//
//	public void setFrequency(int frequency) {
//		this.frequency = frequency;
//	}
//
//	public void setContextFrequency(int contextFrequency) {
//		this.contextFrequency = contextFrequency;
//	}
//
//	public void setInlineFrequency(int inlineFrequency) {
//		this.inlineFrequency = inlineFrequency;
//	}
//
//	public void setTrainingFrequency(int trainingFrequency) {
//		this.trainingFrequency = trainingFrequency;
//	}
//
//	public void setTestFrequency(int testFrequency) {
//		this.testFrequency = testFrequency;
//	}
//
//	public void setSpam(boolean isSpam) {
//		this.isSpam = isSpam;
//	}
//
//	public void setRelatedHashtags(List<String> relatedHashtags) {
//		this.relatedHashtags = relatedHashtags;
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((hashtag == null) ? 0 : hashtag.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Hashtag other = (Hashtag) obj;
//		if (hashtag == null) {
//			if (other.hashtag != null)
//				return false;
//		} else if (!hashtag.equals(other.hashtag))
//			return false;
//		return true;
//	}
//
//	public int compareTo(Object arg0) {
//		if (!(arg0 instanceof Hashtag))
//			return -1;
//		Hashtag ht = (Hashtag) arg0;
//		return ht.frequency - this.frequency;
//	}
//
//}
