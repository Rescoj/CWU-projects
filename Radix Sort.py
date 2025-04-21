#import CountingSort
unsortedArr = [7000,14,392,53,754,100,9,133,2444]
copyarr = unsortedArr.copy()
print(unsortedArr)

def largestRadix(arr):
    radixMax = 0
    for val in unsortedArr:
        if getRadix(val) > radixMax:
            radixMax = getRadix(val)
    return radixMax

def getRadix(num):
    if (num // 10 == 0 and num % 10 != 0):
        return 1
    elif (num // 10 > 0):
        return getRadix(num // 10) + 1

def countSort(arr):
    maxRadix = largestRadix(arr)

    # initializing count array (correct)
    countingSort = []
    for i in range(maxRadix + 1):
        countingSort.append(0)

    # indexing unsortedArr in count array (correct)
    for val in arr:
        countingSort[getRadix(val)] += 1

    # performing cumulative summation on countingSort array
    for i in range(countingSort.__len__()):
        if i == 0:
            continue
        else:
            countingSort[i] = countingSort[i] + countingSort[i - 1]

    # initializing sorted array
    sortedArr = []
    for i in range(arr.__len__()):
        sortedArr.append(0)

    # Sorting the unsorted Array
    for val in arr:
        sortedArr[countingSort[getRadix(val)] - 1] = val
        countingSort[getRadix(val)] -= 1
    return sortedArr

#O(kn) where k is the largest radix in the array
def radixSort(arr):

    #sorting the array from radix sizes first
    arr = countSort(arr)

    #Completing the sort
    #O(kn) where k is the largest radix in the array
    for i in range(largestRadix(arr)):

        #sorting the least significant digit in each collection of numbers with the same radix count
        for j in range(arr.__len__() - 1):
            if getRadix(arr[j]) == getRadix(arr[j + 1]) and arr[j] > arr[j+1]:
                swap = arr[j + 1]
                arr[j + 1] = arr[j]
                arr[j] = swap
                j = 0

    return arr

unsortedArr = radixSort(unsortedArr)
print(unsortedArr)