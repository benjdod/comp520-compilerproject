  0         PUSH         1
  1         PUSH         1
  2         LOADL        0
  3         CALL         newarr  
  4         CALL         L15
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
 21         RETURN (0)   1
 22  L11:   LOADL        102
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
 34         LOAD         -1[LB]
 35         JUMPIF (0)   L13
 36         PUSH         1
 37         LOADL        0
 38         LOAD         -1[LB]
 39         CALL         arraylen
 40         STORE        3[LB]
 41  L12:   LOAD         3[LB]
 42         LOAD         4[LB]
 43         CALL         sub     
 44         JUMPIF (0)   L14
 45         LOAD         -1[LB]
 46         LOAD         4[LB]
 47         CALL         arrayref
 48         CALL         put     
 49         CALL         succ    
 50         JUMP         L12
 51  L13:   LOADL        110
 52         CALL         put     
 53         LOADL        117
 54         CALL         put     
 55         LOADL        108
 56         CALL         put     
 57         LOADL        108
 58         CALL         put     
 59  L14:   CALL         puteol  
 60         RETURN (0)   1
 61  L15:   PUSH         1
 62         LOADL        0
 63         STORE        3[LB]
 64  L16:   LOAD         3[LB]
 65         LOADL        10
 66         CALL         lt      
 67         JUMPIF (0)   L17
 68         LOAD         3[LB]
 69         CALL         L10
 70         LOAD         3[LB]
 71         LOADL        1
 72         CALL         add     
 73         STORE        3[LB]
 74         JUMP         L16
 75  L17:   POP          1
 76         RETURN (0)   1
