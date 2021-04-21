  0         LOADL        0
  1         CALL         newarr  
  2         CALL         L11
  3         HALT   (0)   
  4         PUSH         1
  5         PUSH         1
  6  L10:   LOAD         -1[LB]
  7         CALL         putintnl
  8         RETURN (0)   1
  9  L11:   LOADL        10
 10         CALL         newarr  
 11         STORE        1[SB]
 12         PUSH         1
 13         LOAD         1[SB]
 14         CALL         arraylen
 15         STORE        3[LB]
 16         LOAD         3[LB]
 17         CALL         L10
 18         RETURN (0)   1
