package com.company;

import java.util.*;
import java.util.stream.Collectors;

public class Paper {
	public static void main(String[] args) {

		try{
			Scanner sc = new Scanner(System.in);
			Integer countN = 0, countModify = 0;
			int[] res = getIntArrayFromScanner(2,sc);
			countN = res[0];
			countModify = res[1];
			if(countN<1 || countN>1000)
				throw new Exception("Incorrect count numbers! N mast be between 1 to 1000 ");
			if(countModify<1 || countModify>10000)
				throw new Exception("Incorrect count modify! N mast be between 1 to 10000 ");

			List<Node> list = Arrays.stream(getIntArrayFromScanner(countN, sc)).boxed().map(Node::new).collect(Collectors.toList());
			Long maxVal = list.stream().max(Comparator.comparing(o -> o.modifyValue)).get().modifyValue;

			Integer numberPow = PatterHelp.getCountNumberInValue(maxVal)-1;
			while (numberPow>=0 && countModify>=0){
				Integer pattern = (int)Math.pow(10,numberPow);
				if(list.size()>1)
					list.sort((o1, o2) -> o2.calcCandidateAndReturnDeltaByPattern(pattern).compareTo(o1.calcCandidateAndReturnDeltaByPattern(pattern)));
				else
					list.forEach(a->a.calcCandidateAndReturnDeltaByPattern(pattern));
				for (Node node : list) {
					if (countModify>0 && node.applyVal()){
						countModify--;
					}
				}
				numberPow --;
			}
			Long sumOld = list.stream().mapToLong(a->a.oldValue).sum();
			Long sumNew = list.stream().mapToLong(a->a.modifyValue).sum();
			Long sumDelta = sumNew - sumOld;
			System.out.println(sumDelta);
		}catch (Exception exp){
			System.out.println(exp.getMessage());
		}
	}
	private static int[] getIntArrayFromScanner(Integer countMustBeElement, Scanner sc) throws IllegalArgumentException {
		try {
			String line = sc.nextLine();

			int[] data = Arrays.stream(line.split("\\s"))
					.filter(a->!a.isEmpty())
					.limit(countMustBeElement)
					.mapToInt(Integer::parseInt)
					.toArray();
			if(data.length!=countMustBeElement)
				throw new Exception("Count element must be "+countMustBeElement);
			return data;

		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	private static class Node{
		private Long oldValue;
		private Long modifyValue;
		private Long candidateValue = 0L;

		public Node(Integer oldValue) {
			this.oldValue = oldValue.longValue();
			this. modifyValue = oldValue.longValue();
		}

		public Long getOldValue() {
			return oldValue;
		}

		public Long getModifyValue() {
			return modifyValue;
		}

		public Long calcCandidateAndReturnDeltaByPattern(Integer pattern){
			candidateValue = PatterHelp.getNewValueByPattern(modifyValue, pattern);
			return candidateValue - modifyValue;
		}
		public Boolean applyVal(){
			if(!Objects.equals(modifyValue, candidateValue)){
				modifyValue = candidateValue;
				return true;
			}
			return false;
		}
	}

	private static class PatterHelp{

		public static Long getNewValueByPattern(Long value, Integer pattern){
			if(value<pattern) return value;
			Long tmp = value / pattern;
			if(tmp<1 || tmp == 9) return value;
			else if(tmp < 9){
				return pattern*(9 - tmp)+value;
			}else{
					return pattern*(9 -(tmp%10))+value;
			}
		}

		public static Integer getCountNumberInValue(Long value){
			Integer log = (int)(Math.log10(value)+1);
			return log;
		}
	}
}
