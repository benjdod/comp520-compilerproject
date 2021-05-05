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
 42         PUSH         1
 43         LOADL        0
 44         STORE        4[LB]
 45         JUMP         L15
 46  L14:   LOAD         4[LB]
 47         LOADL        1
 48         CALL         sub     
 49         STORE        4[LB]
 50  L15:   PUSH         1
 51         LOAD         4[LB]
 52         STORE        5[LB]
 53         JUMP         L17
 54  L16:   LOAD         5[LB]
 55         LOADL        1
 56         CALL         add     
 57         STORE        5[LB]
 58  L17:   LOAD         5[LB]
 59         LOADL        0
 60         CALL         lt      
 61         JUMPIF (0)   L19
 62         LOAD         5[LB]
 63         LOADL        4
 64         CALL         neg     
 65         CALL         gt      
 66         JUMPIF (0)   L18
 67         JUMP         L16
 68  L18:   LOAD         5[LB]
 69         CALL         L11
 70         JUMP         L16
 71  L19:   POP          1
 72         LOAD         4[LB]
 73         LOADL        5
 74         CALL         neg     
 75         CALL         lt      
 76         JUMPIF (0)   L20
 77         JUMP         L21
 78  L20:   JUMP         L14
 79  L21:   POP          1
 80         RETURN (0)   1
