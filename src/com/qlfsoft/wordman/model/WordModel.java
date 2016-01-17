package com.qlfsoft.wordman.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * µ•¥ –≈œ¢
 * @author qlf
 *
 */
public class WordModel implements Parcelable {
	private int wordId;
	private String word;
	private String description;
	private String phonetic;
	private String sentence;
	
	public int getWordId() {
		return wordId;
	}
	public void setWordId(int wordId) {
		this.wordId = wordId;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPhonetic() {
		return phonetic;
	}
	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel out, int flags) {
		
		out.writeInt(wordId);
		out.writeString(word);
		out.writeString(phonetic);
		out.writeString(description);
		out.writeString(sentence);
	}
	
	public static final Parcelable.Creator<WordModel> CREATOR= new Creator<WordModel>()
	{

		@Override
		public WordModel createFromParcel(Parcel source) {
			return new WordModel(source);
		}

		@Override
		public WordModel[] newArray(int size) {
			// TODO Auto-generated method stub
			return new WordModel[size];
		}
		
	};
	
	public WordModel(Parcel in)
	{
		wordId = in.readInt();
		word = in.readString();
		phonetic = in.readString();
		description = in.readString();
		sentence = in.readString();
	}
	public WordModel()
	{
		
	}
}
