  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L13
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
 39  L13:   PUSH         1
 40         LOADL        56
 41         STORE        3[LB]
 42         LOAD         3[LB]
 43         LOAD         3[LB]
 44         CALL         sub     
 45         STORE        3[LB]
 46         LOAD         3[LB]
 47         LOADL        2
 48         CALL         add     
 49         STORE        3[LB]
 50         LOAD         3[LB]
 51         LOADL        20
 52         CALL         mult    
 53         STORE        3[LB]
 54         LOAD         3[LB]
 55         LOADL        4
 56         CALL         div     
 57         STORE        3[LB]
 58         LOAD         3[LB]
 59         LOADL        3
 60         CALL         mod     
 61         STORE        3[LB]
 62         LOAD         3[LB]
 63         CALL         L11
 64         RETURN (0)   1
