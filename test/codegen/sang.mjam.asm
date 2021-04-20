  0         LOADL        0
  1         CALL         newarr  
  2         CALL         L10
  3         HALT   (0)   
  4         PUSH         1
  5         LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8  L10:   RETURN (0)   0
