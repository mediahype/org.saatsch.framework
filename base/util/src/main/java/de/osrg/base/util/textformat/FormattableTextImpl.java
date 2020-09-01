package de.osrg.base.util.textformat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FormattableTextImpl implements FormattableText {

	private StringBuilder unformatted = new StringBuilder();

	private List<Format> formats = new ArrayList<Format>();

	@Override
	public List<Format> getFormats() {
		Collections.sort(formats);
		return formats;
	}
	

	public String getUnformatted() {
		return unformatted.toString();
	}


	public FormattableText insert(Insertable insert, int index) {
		
		if (index > unformatted.length()){
			throw new IndexOutOfBoundsException("cannot insert after end");
		}
		
		formats.add(new Format(insert.getTag(), index));
		return this;
	}

	public FormattableText apply(Applyable apply, int beginIndex, int endIndex) {
		if (endIndex < beginIndex) {
			return this;
		}

		formats.add(new Format(apply.getEndTag(), endIndex));
		formats.add(new Format(apply.getStartTag(), beginIndex));
		
		return this;
	}

	public FormattableText append(String append) {
		unformatted.append(append);
		return this;
	}


	@Override
	public FormattableText insert(String toInsert, int atIndex) {
		
		if (atIndex > unformatted.length()){
			throw new IndexOutOfBoundsException("cannot insert after end");
		}
		
		return null;
	}




}
