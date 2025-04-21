unsortedArr = [1,5,8,2,4,8,9,0]
max = 0
print("unsorted Array = " + str(unsortedArr))
#getting max in unsorted arr for the count array
for val in unsortedArr:
    if max < val:
        max = val

#initializing count array (correct)
countingSort = []
for i in range(max + 1):
    countingSort.append(0)

#indexing unsortedArr in count array (correct)
for val in unsortedArr:
    countingSort[val] += 1

#performing cumulative summation on countingSort array
for i in range(countingSort.__len__()):
    if i == 0:
        continue
    else:
        countingSort[i] = countingSort[i] + countingSort[i - 1]

#initializing sorted array
sortedArr = []
for i in range(unsortedArr.__len__()):
    sortedArr.append(0)

#Sorting the unsorted Array
for val in unsortedArr:
    sortedArr[countingSort[val] - 1] = val
    countingSort[val] -= 1
print("sorted Array = " + str(sortedArr))
