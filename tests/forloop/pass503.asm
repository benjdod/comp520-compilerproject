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
 40         LOADL        0
 41         STORE        3[LB]
 42         JUMP         L15
 43  L14:   LOAD         3[LB]
 44         LOADL        1
 45         CALL         add     
 46         STORE        3[LB]
 47  L15:   LOAD         3[LB]
 48         LOADL        6
 49         CALL         lt      
 50         JUMPIF (0)   L22
 51         PUSH         1
 52         LOADL        0
 53         STORE        4[LB]
 54         JUMP         L17
 55  L16:   LOAD         4[LB]
 56         LOADL        1
 57         CALL         add     
 58         STORE        4[LB]
 59  L17:   LOAD         4[LB]
 60         LOAD         3[LB]
 61         CALL         lt      
 62         JUMPIF (0)   L21
 63         PUSH         1
 64         LOADL        0
 65         STORE        5[LB]
 66         JUMP         L19
 67  L18:   LOAD         5[LB]
 68         LOADL        1
 69         CALL         add     
 70         STORE        5[LB]
 71  L19:   LOAD         5[LB]
 72         LOAD         4[LB]
 73         CALL         lt      
 74         JUMPIF (0)   L20
 75         LOAD         3[LB]
 76         LOAD         4[LB]
 77         CALL         add     
 78         LOAD         5[LB]
 79         CALL         add     
 80         CALL         L11
 81         JUMP         L18
 82  L20:   POP          1
 83         JUMP         L16
 84  L21:   POP          1
 85         JUMP         L14
 86  L22:   POP          1
 87         RETURN (0)   1
