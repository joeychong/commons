package com.ebizzone.json;

public class JSONFilter implements JSONUtils.Filter {
	public enum FilterMode {
		include, exclude;
	}
	
	private FilterMode mFilterMode = FilterMode.exclude;
	private String[] mFilters;
	
	public JSONFilter(FilterMode mode, String... filters) {
		mFilterMode = mode;
		mFilters = filters;
	}
	
	@Override
	public boolean filter(String[] tags) {
		if (mFilters.length > 0) {
			
			int count = 0;
			for (String m : tags) {
				for (String f : mFilters) {
					if (m.equals(f)) {
						count ++;
					}
				}
			}
			
			if (count != mFilters.length) {
				if (mFilterMode == FilterMode.include) {
					return true;
				} else {
					
				}
			} else {
				if (mFilterMode == FilterMode.include) {
					
				} else {
					return true;
				}
			}
		}
		
		return false;
	}
}
