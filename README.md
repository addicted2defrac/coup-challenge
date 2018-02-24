# COUP Challenge: Find number of Fleet Engineers

You are given a int[] scooters, which has as many elements as there are
districts in Berlin that Coup operates in. For each i, scooters[i] is the
number of scooters in that district (0-based index).
During a work day, scooters are maintained (batteries changed, cleaned,
checked for damages) by the Fleet Manager (FM) and possibly other Fleet
Engineers (FEs). Each FE, as well as the FM, can only maintain scooters in
one district. Additionally, there is a limit on how many scooters a single
FE may supervise: the FM is able to maintain up to C scooters, and a FE is
able to maintain up to P scooters. Each scooter has to be maintained by some FE or the FM.

#How do we solve the problem?
Find the minimum number of FEs which are required to help the FM so that every scooter in
each district of Berlin is maintained. Note that you may choose which district the FM should
go to.

##Input / Output
As input you are given the []int scooters, int C and int P
Result should be int - the minimum number of FEs which are required to help the FM

##Constraints
[]scooters will contain between 1 and 100 elements.
Each element in scooters will be between 0 and 1000.
C will be between 1 and 999.
P will be between 1 and 1000.

##Examples
1)
input:
{ scooters: [15, 10],
C: 12,
P: 5
}
expected output:
{ fleet_engineers: 3 }

2)
input:
{ scooters: [11, 15, 13],
C: 9,
P: 5
}
expected output:
{ fleet_engineers: 7 }
