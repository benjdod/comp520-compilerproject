  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L13
  4         HALT   (0)   
  5         LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8         CALL         puteol  
  9         RETURN (0)   0
 10         LOAD         -1[LB]
 11         JUMPIF (0)   L10
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
 22  L10:   LOADL        102
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
 34         PUSH         1
 35         LOADL        0
 36         LOAD         -1[LB]
 37         CALL         arraylen
 38         STORE        3[LB]
 39  L11:   LOAD         3[LB]
 40         LOAD         4[LB]
 41         CALL         sub     
 42         JUMPIF (0)   L12
 43         LOAD         -1[LB]
 44         LOAD         4[LB]
 45         CALL         arrayref
 46         CALL         put     
 47         CALL         succ    
 48         JUMP         L11
 49  L12:   CALL         puteol  
 50         RETURN (0)   1
 51  L13:   PUSH         1
 52         LOADL        1
 53         STORE        3[LB]
 54         LOADL        1
 55         LOAD         -1[ST]
 56         JUMPIF (0)   L14
 57         LOADL        1
 58         CALL         and     
 59         LOAD         -1[ST]
 60         JUMPIF (0)   L14
 61         LOADL        0
 62         CALL         and     
 63         LOAD         -1[ST]
 64         JUMPIF (0)   L14
 65         LOADL        0
 66         CALL         and     
 67         LOAD         -1[ST]
 68         JUMPIF (0)   L14
 69         LOADL        0
 70         CALL         and     
 71         LOAD         -1[ST]
 72         JUMPIF (1)   L15
 73  L14:   LOADL        1
 74         CALL         or      
 75  L15:   JUMPIF (0)   L16
 76         LOADL        100
 77         STORE        3[LB]
 78         JUMP         L17
 79  L16:   LOADL        100
 80         CALL         neg     
 81         STORE        3[LB]
 82  L17:   RETURN (0)   1
