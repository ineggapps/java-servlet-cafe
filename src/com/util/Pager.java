package com.util;

import java.util.ArrayList;
import java.util.List;

public class Pager {
	public int pageCount(int rows, int dataCount) {
		// 데이터의 개수가 0인 경우 처리 방법
		if (dataCount <= 0) {
			return 0;
		}
		// 총 페이지 수 = dataCount / 행 수
		// 그런데 dataCount / 행 수의 나머지가 존재하면 +1을 더 해야 한다.
		// ex:97개의 데이터가 있으면 97/10=9가 아니라 9+1을 한 10이 나와야 한다는 것이다.
		return dataCount / rows + (dataCount % rows > 0 ? 1 : 0);
	}

	public int[] paging(int current_page, int total_page) {
		return paging(10, current_page, total_page);
	}

	public int[] paging(int rows, int current_page, int total_page) {

//		int center = (rows / 2)!=0?rows/2:0;
//		int start = current_page - center > 0 ? current_page - center : 1;
//		int end;
//		int page = start;

		int maxShowPage = 9; // 보이는 페이지 개수 말하는 거임!!
		int length; // 현재 배열의 개수
		int centerPosition = 5;// 가운데 위치 (5번째)
		int start, end;// 시작, 끝 페이지
		start = current_page - centerPosition + 1 > 0 ? current_page - centerPosition + 1 : 1;
		end = current_page + centerPosition - 1 < total_page ? current_page + centerPosition - 1 : total_page;
		length = end - start + 1; //앞자리가 모자라면
		if(length<maxShowPage) {
			end += maxShowPage - length;
		}
		if(end>total_page) {
			end = total_page;
		}
//		System.out.println(String.format("start=%s, end=%s, length=%s / total_page=%s",start,end,length, total_page));

		List<Integer> pages = new ArrayList<>();
		int page = start;
		while (page <= end) {
//		while(page <= total_page && page < start+numPerBlock) {
			pages.add(page++);
		}

		return pages.stream().mapToInt(i -> i).toArray();
	}
}

/*
 * 95페이지까지 있다고 가정 1 2 3 4 5 6 7 8 9 10 [처음] [이전] 6 7 8 9 10 11 12 13 14 15 [다음]
 * [끝]
 * 
 * 
 * [처음] [이전] 91 92 93 94 95
 */