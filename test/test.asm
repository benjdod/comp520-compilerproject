  0         PUSH         1
  1         PUSH         1
  2         LOADL        0
  3         CALL         newarr  
  4         CALL         L12
  5         HALT   (0)   
  6  L10:   LOAD         -1[LB]
  7         CALL         putintnl
  8         RETURN (0)   1
  9         CALL         puteol  
 10         RETURN (0)   0
 11         LOAD         -1[LB]
 12         JUMPIF (0)   L11
 13         LOADL        116
 14         CALL         put     
 15         LOADL        114
 16         CALL         put     
 17         LOADL        117
 18         CALL         put     
 19         LOADL        101
 20         CALL         put     
 21         CALL         puteol  
 22         RETURN (0)   1
 23  L11:   LOADL        102
 24         CALL         put     
 25         LOADL        97
 26         CALL         put     
 27         LOADL        108
 28         CALL         put     
 29         LOADL        115
 30         CALL         put     
 31         LOADL        101
 32         CALL         put     
 33         CALL         puteol  
 34         RETURN (0)   1
 35  L12:   PUSH         1
 36         LOADL        1
 37         STORE        3[LB]
 38  L13:   LOAD         3[LB]
 39         LOADL        10
 40         CALL         lt      
 41         JUMPIF (0)   L15
 42         LOAD         3[LB]
 43         LOADL        7
 44         CALL         gt      
 45         JUMPIF (0)   L14
 46         LOADL        9
 47         STORE        3[LB]
 48         JUMP         L13
 49  L14:   LOAD         3[LB]
 50         LOADL        1
 51         CALL         add     
 52         STORE        3[LB]
 53         JUMP         L13
 54  L15:   LOAD         3[LB]
 55         CALL         L10
 56         RETURN (0)   1
