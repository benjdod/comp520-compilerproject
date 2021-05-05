  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L14
  4         HALT   (0)   
  5  L11:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8  L12:   CALL         puteol  
  9         RETURN (0)   0
 10         LOAD         -1[LB]
 11         JUMPIF (0)   L13
 12         LOADL        116
 13         CALL         put     
 14         LOADL        114
 15         CALL         put     
 16         LOADL        117
 17         CALL         put     
 18         LOADL        101
 19         CALL         put     
 20         RETURN (0)   1
 21  L13:   LOADL        102
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
 39  L14:   PUSH         1
 40         LOADL        0
 41         STORE        3[LB]
 42         PUSH         1
 43         LOADL        20
 44         STORE        4[LB]
 45         JUMP         L16
 46  L15:   LOAD         3[LB]
 47         LOADL        1
 48         CALL         add     
 49         STORE        3[LB]
 50         LOAD         4[LB]
 51         LOADL        1
 52         CALL         sub     
 53         STORE        4[LB]
 54  L16:   LOAD         3[LB]
 55         LOAD         4[LB]
 56         CALL         le      
 57         JUMPIF (0)   L17
 58         LOAD         3[LB]
 59         CALL         L11
 60         LOAD         4[LB]
 61         CALL         L11
 62         CALL         L12
 63         JUMP         L15
 64  L17:   POP          2
 65         RETURN (0)   1
