  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L15
  4         HALT   (0)   
  5  L11:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8         CALL         puteol  
  9         RETURN (0)   0
 10         LOAD         -1[LB]
 11         JUMPIF (0)   L12
 12         LOADL        116
 13         CALL         put     
 14         LOADL        114
 15         CALL         put     
 16         LOADL        117
 17         CALL         put     
 18         LOADL        101
 19         CALL         put     
 20         RETURN (0)   1
 21  L12:   LOADL        102
 22         CALL         put     
 23         LOADL        97
 24         CALL         put     
 25         LOADL        108
 26         CALL         put     
 27         LOADL        115
 28         CALL         put     
 29         LOADL        101
 30         CALL         put     
 31         CALL         puteol  
 32         RETURN (0)   1
 33         LOAD         -1[LB]
 34         JUMPIF (0)   L10
 35         LOAD         -1[LB]
 36         CALL         putarr  
 37         CALL         puteol  
 38         RETURN (0)   1
 39  L13:   LOADA        0[OB]
 40         LOADL        0
 41         CALL         fieldref
 42         RETURN (1)   0
 43  L14:   LOADA        0[OB]
 44         LOADL        0
 45         CALL         fieldref
 46         LOAD         -1[LB]
 47         CALL         add     
 48         RETURN (1)   1
 49  L15:   PUSH         1
 50         LOADL        -1
 51         LOADL        1
 52         CALL         newobj  
 53         STORE        3[LB]
 54         LOAD         3[LB]
 55         LOADL        0
 56         LOADL        5
 57         CALL         fieldupd
 58         LOAD         3[LB]
 59         CALLI        L13
 60         LOADL        5
 61         CALL         neg     
 62         LOAD         3[LB]
 63         CALLI        L14
 64         CALL         add     
 65         CALL         L11
 66         RETURN (0)   1
