  0         PUSH         1
  1         PUSH         1
  2         PUSH         1
  3         LOADL        0
  4         CALL         newarr  
  5         CALL         L15
  6         HALT   (0)   
  7  L10:   LOAD         -1[LB]
  8         CALL         putintnl
  9         RETURN (0)   1
 10         CALL         puteol  
 11         RETURN (0)   0
 12         LOAD         -1[LB]
 13         JUMPIF (0)   L11
 14         LOADL        116
 15         CALL         put     
 16         LOADL        114
 17         CALL         put     
 18         LOADL        117
 19         CALL         put     
 20         LOADL        101
 21         CALL         put     
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
 35         LOAD         -1[LB]
 36         JUMPIF (0)   L13
 37         PUSH         1
 38         LOADL        0
 39         LOAD         -1[LB]
 40         CALL         arraylen
 41         STORE        3[LB]
 42  L12:   LOAD         3[LB]
 43         LOAD         4[LB]
 44         CALL         sub     
 45         JUMPIF (0)   L14
 46         LOAD         -1[LB]
 47         LOAD         4[LB]
 48         CALL         arrayref
 49         CALL         put     
 50         CALL         succ    
 51         JUMP         L12
 52  L13:   LOADL        110
 53         CALL         put     
 54         LOADL        117
 55         CALL         put     
 56         LOADL        108
 57         CALL         put     
 58         LOADL        108
 59         CALL         put     
 60  L14:   CALL         puteol  
 61         RETURN (0)   1
 62  L15:   PUSH         1
 63         LOADL        0
 64         STORE        3[LB]
 65         PUSH         1
 66         LOADL        60
 67         STORE        4[LB]
 68         JUMP         L17
 69  L16:   LOAD         3[LB]
 70         LOADL        1
 71         CALL         add     
 72         STORE        3[LB]
 73  L17:   LOAD         3[LB]
 74         LOAD         4[LB]
 75         CALL         lt      
 76         JUMPIF (0)   L19
 77         LOAD         3[LB]
 78         LOADL        20
 79         CALL         gt      
 80         JUMPIF (0)   L18
 81         JUMP         L16
 82  L18:   LOAD         3[LB]
 83         CALL         L10
 84         JUMP         L16
 85  L19:   POP          2
 86         RETURN (0)   1
