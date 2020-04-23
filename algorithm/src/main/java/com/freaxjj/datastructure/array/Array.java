package com.freaxjj.datastructure.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘亚林
 * @description
 * @create 2020/4/2 15:10
 **/
public class Array {
    /**
     * @param nums: A list of integers
     * @param k: An integer
     * @return: The majority number
     */
    public static int majorityNumber(List<Integer> nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int limit = nums.size() / k;

        for(Integer num : nums){
            Integer times = map.get(num);
            if(times == null){
                times = 1;
            }else {
                times ++;
            }
            if(times > limit){
                return num;
            }
            map.put(num, times);
        }
        return 0;
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(3,1,2,3,2,3,3,4,4,4);
        System.out.println(majorityNumber(list, 3));
    }
}
