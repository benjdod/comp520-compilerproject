  0         PUSH         1
  1         PUSH         1
  2         LOADL        0
  3         CALL         newarr  
  4         CALL         L16
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
 21         CALL         puteol  
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
 35  L12:   LOAD         1[SB]
 36         CALL         not     
 37         STORE        1[SB]
 38         LOAD         1[SB]
 39         RETURN (1)   0
 40  L13:   CALL         L12
 41         JUMPIF (0)   L14
 42         LOADL        5
 43         JUMP         L15
 44  L14:   LOADL        5
 45         CALL         neg     
 46  L15:   RETURN (1)   0
 47  L16:   CALL         L13
 48         CALL         L10
 49         CALL         L13
 50         CALL         L10
 51         RETURN (0)   1
