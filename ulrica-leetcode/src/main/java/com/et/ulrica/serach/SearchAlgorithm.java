package com.et.ulrica.serach;

/**
 * @author:ulrica
 * @date: 2022/7/27 上午9:44
 * @description:
 * @version:1.0
 **/

/**
 *
 */
public class SearchAlgorithm {

  public static int sequenceSearch(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] == target) {
        return i;
      }
    }
    return -1;
  }

  /**
   * link{https://leetcode.cn/problems/binary-search/} ideas: Binary search is mainly suitable for
   * an ascending array.Firstly , used right 、left record this search range, left <= right for
   * search all num.And mid record the middle index of number array, check number with target.If
   * nums[mid] == target , we can get search result and return index, else if nums[mid] > target, we
   * should reset right = mid -1,or else we should reset left = mid + 1.
   *
   * @param nums
   * @param target
   * @return
   */
  public static int binarySearch(int[] nums, int target) {
    int right = nums.length - 1;
    int left = 0;
    int mid = left + (right - left) / 2;
    while (left <= right) {
      if (nums[mid] == target) {
        return mid;
      } else if (nums[mid] > target) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }
      mid = left + (right - left) / 2;
    }
    return -1;
  }

  public static int searchInsert(int[] nums, int target) {
    int right = nums.length - 1;
    int left = 0;
    int mid = 0;
    while (left <= right) {
      mid = left + (right - left) / 2;
      if (nums[mid] == target) {
        return mid;
      } else if (nums[mid] > target) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }
    if (nums[mid] > target) {
      return mid;
    } else {
      return mid + 1;
    }
  }
}
