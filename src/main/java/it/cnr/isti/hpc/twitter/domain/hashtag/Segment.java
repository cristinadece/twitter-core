///**
// *  Copyright 2011 Diego Ceccarelli
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
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
///**
// * Segment.java
// *
// * @author Diego Ceccarelli, diego.ceccarelli@isti.cnr.it
// * created on 23/set/2011
// */
//public class Segment implements Iterable {
//	/**
//	 * Logger for this class
//	 */
//	private static final Logger logger = LoggerFactory.getLogger(Segment.class);
//	private static  String text = null;
//	List<Term> terms = new ArrayList<Term>();
//
//	
//	private Segment(){}
//	
//	
//	public Segment(Term t) {
//		terms = new ArrayList<Term>();
//		terms.add(t);
//	}
//	
//	public static void setText(String text){
//		Segment.text = text;
//	}
//	
//	public static String getText(){
//		return text;
//	}
//
//
//
//	public Segment clone(){
//		Segment s = new Segment();
//		s.terms = new ArrayList<Term>(terms);
//		return s;
//	}
//	
//	public int getEnd(){
//		return getLastTerm().getEnd();
//	}
//
//	public Segment append(Term left, Term right) {
//		if (getLastTerm().equals(left)){
//			Segment s = clone();
//			s.terms.add(right);
//			return s;
//		}
//		return null;
//	}
//
//	
//	
//	public int size() {
//		return terms.size();
//	}
//	
//	public Term getLastTerm(){
//		return terms.get(terms.size()-1);
//	}
//
//	public Iterator iterator() {
//		return terms.iterator();
//	}
//
//	public double getScore() {
//		double total = 0;
//		for (Term t : terms) {
//			total += t.getScore();
//		}
//		return total / terms.size();
//	}
//
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((terms == null) ? 0 : terms.hashCode());
//		return result;
//	}
//
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Segment other = (Segment) obj;
//		if (terms == null) {
//			if (other.terms != null)
//				return false;
//		} else if (!terms.equals(other.terms))
//			return false;
//		return true;
//	}
//	
//	public boolean containsThreeTermsOfLength(int length){
//		int count = 0;
//		for (Term t : terms){
//			if (t.length() == length) count++;
//			else count = 0;
//			if (count == 3) return true;
//		}
//		return false;
//	}
//
//
//	public String toString() {
//		StringBuilder sb = new StringBuilder();
//		for (Term t : terms){
//			sb.append(t).append(" ");
//		}
//		
//		if (getEnd() != text.length()) sb.append(text.substring(getEnd()));
//		return sb.toString();
//	}
//
//	
//
//}
