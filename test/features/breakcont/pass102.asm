  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L13
  4         HALT   (0)   
  5  L10:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8  L11:   CALL         puteol  
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
 20         CALL         puteol  
 21         RETURN (0)   1
 22  L12:   LOADL        102
 23         CALL         put     
 24         LOADL        97
 25         CALL         put     
 26         LOADL        108
 27         CALL         put     
 28         LOADL        115
 29         CALL         put     
 30         LOADL        101
 31         CALL         put     
 32         CALL         puteol  
 33         RETURN (0)   1
 34  L13:   PUSH         1
 35         LOADL        0
 36         STORE        3[LB]
 37         PUSH         1
 38         LOADL        0
 39         STORE        4[LB]
 40  L14:   LOADL        1
 41         JUMPIF (0)   L21
 42         LOADL        0
 43         STORE        4[LB]
 44  L15:   LOADL        1
 45         JUMPIF (0)   L19
 46         LOAD         4[LB]
 47         LOADL        3
 48         CALL         gt      
 49         JUMPIF (0)   L17
 50         JUMP         L19
 51  L16:   JUMP         L18
 52  L17:   LOAD         4[LB]
 53         LOADL        1
 54         CALL         add     
 55         STORE        4[LB]
 56  L18:   JUMP         L15
 57  L19:   LOAD         3[LB]
 58         CALL         L10
 59         LOAD         4[LB]
 60         CALL         L10
 61         LOAD         3[LB]
 62         LOAD         4[LB]
 63         CALL         gt      
 64         JUMPIF (0)   L20
 65         JUMP         L21
 66  L20:   LOAD         3[LB]
 67         LOADL        1
 68         CALL         add     
 69         STORE        3[LB]
 70         JUMP         L14
 71  L21:   CALL         L11
 72         LOAD         3[LB]
 73         CALL         L10
 74         RETURN (0)   1
