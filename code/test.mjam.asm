  0         LOADL        0
  1         CALL         newarr  
  2         CALL         L11
  3         HALT   (0)   
  4  L10:   LOAD         -1[LB]
  5         LOADL        1
  6         CALL         add     
  7         RETURN (1)   1
  8  L11:   PUSH         3
  9         LOADL        -1
 10         LOADL        1
 11         CALL         newobj  
 12         LOADL        5
 13         CALL         L10
 14         CALL         putintnl
 15         RETURN (0)   0
