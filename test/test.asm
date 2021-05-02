  0         PUSH         1
  1         PUSH         1
  2         LOADL        0
  3         CALL         newarr  
  4         CALL         L12
  5         HALT   (0)   
  6         LOAD         -1[LB]
  7         CALL         putintnl
  8         RETURN (0)   1
  9  L10:   LOADL        1
 10         RETURN (1)   0
 11  L11:   LOADL        1
 12         LOAD         -1[LB]
 13         CALL         add     
 14         STORE        1[SB]
 15         LOADL        1
 16         LOAD         -1[LB]
 17         CALL         add     
 18         RETURN (1)   1
 19  L12:   LOADL        4
 20         CALL         L11
 21         POP          1
 22         CALL         L10
 23         POP          1
 24         RETURN (0)   1
