  0         PUSH         1
  1         PUSH         1
  2         LOADL        0
  3         CALL         newarr  
  4         CALL         L12
  5         HALT   (0)   
  6         LOAD         -1[LB]
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
 35         JUMPIF (0)   L10
 36         LOAD         -1[LB]
 37         CALL         putarr  
 38         CALL         puteol  
 39         RETURN (0)   1
 40  L12:   LOADL        10
 41         CALL         L16
 42         PUSH         1
 43         LOADL        0
 44         STORE        3[LB]
 45         PUSH         1
 46         LOADL        10
 47         CALL         newarr  
 48         STORE        4[LB]
 49         JUMP         L14
 50  L13:   LOAD         3[LB]
 51         LOADL        1
 52         CALL         add     
 53         STORE        3[LB]
 54  L14:   LOAD         3[LB]
 55         LOAD         4[LB]
 56         CALL         arraylen
 57         CALL         lt      
 58         JUMPIF (0)   L15
 59         LOAD         4[LB]
 60         LOAD         3[LB]
 61         LOAD         3[LB]
 62         LOADL        3
 63         CALL         mod     
 64         CALL         arrayupd
 65         PUSH         1
 66         CALL         L17
 67         STORE        5[LB]
 68         LOAD         5[LB]
 69         LOADL        9
 70         LOAD         3[LB]
 71         CALL         sub     
 72         LOADL        14
 73         CALL         arrayupd
 74         POP          1
 75         JUMP         L13
 76  L15:   POP          1
 77         RETURN (0)   1
 78  L16:   LOAD         -1[LB]
 79         CALL         newarr  
 80         STORE        1[SB]
 81         RETURN (0)   1
 82  L17:   LOAD         1[SB]
 83         RETURN (1)   0
