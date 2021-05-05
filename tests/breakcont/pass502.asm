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
 43         LOADL        0
 44         STORE        4[LB]
 45  L15:   LOADL        1
 46         JUMPIF (0)   L22
 47         LOADL        0
 48         STORE        4[LB]
 49  L16:   LOADL        1
 50         JUMPIF (0)   L20
 51         LOAD         4[LB]
 52         LOADL        3
 53         CALL         gt      
 54         JUMPIF (0)   L18
 55         JUMP         L20
 56  L17:   JUMP         L19
 57  L18:   LOAD         4[LB]
 58         LOADL        1
 59         CALL         add     
 60         STORE        4[LB]
 61  L19:   JUMP         L16
 62  L20:   LOAD         3[LB]
 63         CALL         L11
 64         LOAD         4[LB]
 65         CALL         L11
 66         LOAD         3[LB]
 67         LOAD         4[LB]
 68         CALL         gt      
 69         JUMPIF (0)   L21
 70         JUMP         L22
 71  L21:   LOAD         3[LB]
 72         LOADL        1
 73         CALL         add     
 74         STORE        3[LB]
 75         JUMP         L15
 76  L22:   CALL         L12
 77         LOAD         3[LB]
 78         CALL         L11
 79         RETURN (0)   1
