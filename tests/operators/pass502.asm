  0         PUSH         1
  1         PUSH         1
  2         LOADL        0
  3         CALL         newarr  
  4         CALL         L17
  5         HALT   (0)   
  6  L11:   LOAD         -1[LB]
  7         CALL         putintnl
  8         RETURN (0)   1
  9         CALL         puteol  
 10         RETURN (0)   0
 11         LOAD         -1[LB]
 12         JUMPIF (0)   L12
 13         LOADL        116
 14         CALL         put     
 15         LOADL        114
 16         CALL         put     
 17         LOADL        117
 18         CALL         put     
 19         LOADL        101
 20         CALL         put     
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
 34         LOAD         -1[LB]
 35         JUMPIF (0)   L10
 36         LOAD         -1[LB]
 37         CALL         putarr  
 38         CALL         puteol  
 39         RETURN (0)   1
 40  L13:   LOAD         1[SB]
 41         CALL         not     
 42         STORE        1[SB]
 43         LOAD         1[SB]
 44         RETURN (1)   0
 45  L14:   CALL         L13
 46         JUMPIF (0)   L15
 47         LOADL        5
 48         JUMP         L16
 49  L15:   LOADL        5
 50         CALL         neg     
 51  L16:   RETURN (1)   0
 52  L17:   CALL         L14
 53         CALL         L11
 54         CALL         L14
 55         CALL         L11
 56         RETURN (0)   1
