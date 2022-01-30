package com.company;

import java.util.*;
import java.util.stream.Collectors;

public class Floors {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Integer countEmployers = 0 , timeOutEmployer = 0, numberFastEmployer= 0;
        List<Integer> floors = new LinkedList<>();

        try{
            int[] countEmpAndTimeout =  getIntArrayFromScanner(2, sc);
            countEmployers = countEmpAndTimeout[0];
            timeOutEmployer = countEmpAndTimeout[1];
            if(countEmployers<2){
                System.out.println("Employers must be most 1");
                return;
            }
            if(timeOutEmployer>100 || timeOutEmployer<0){
                System.out.println("illegal timeout");
                return;
            }

            floors.addAll(Arrays.stream(getIntArrayFromScanner(countEmployers, sc)).boxed().collect(Collectors.toList()));

            if (!floors.stream().allMatch(a->a >0 && a <=100)){
                System.out.println("Employers floor must be 1 to 100");
                return;
            }
            Set<Integer> floorsUnic = new HashSet<>(floors);
            if (floorsUnic.size()!=floors.size()){
                System.out.println("Employers floor must be unic");
                return;
            }
            numberFastEmployer = getIntArrayFromScanner(1, sc)[0];
            if(numberFastEmployer>floors.size()||numberFastEmployer<1){
                System.out.println("Invalid number employer");
                return;
            }

            Integer floorFastEmp = floors.stream().skip(numberFastEmployer-1).findFirst().orElseThrow(()->new IllegalArgumentException("Not found element"));

            floors.remove(floorFastEmp);
            Integer minFloor = floors.stream().min(Comparator.naturalOrder()).orElseThrow(()->new IllegalArgumentException("Not found element"));
            Integer maxFloor = floors.stream().max(Comparator.naturalOrder()).orElseThrow(()->new IllegalArgumentException("Not found element"));

            if(floorFastEmp-1>=timeOutEmployer)
            {
                if(maxFloor<floorFastEmp){
                    System.out.println(floorFastEmp-minFloor);
                }else if (floorFastEmp-minFloor>maxFloor-floorFastEmp){
                    System.out.println((maxFloor-floorFastEmp)+(maxFloor-minFloor));
                }else{
                    System.out.println((floorFastEmp-minFloor)+(maxFloor-minFloor));
                }
            }else{
                System.out.println(maxFloor-minFloor);
            }
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
}