  0         LOADL        0
  1         CALL         newarr  
  2         CALL         L15
  3         HALT   (0)   
  4         PUSH         1
  5  L10:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8  L11:   LOADL        3
  9         STORE        0[OB]
 10  L12:   LOAD         0[OB]
 11         LOADL        10
 12         CALL         lt      
 13         JUMPIF (0)   L13
 14         PUSH         1
 15         LOADA        0[OB]
 16         CALLI        L14
 17         STORE        3[LB]
 18         POP          1
 19         JUMP         L12
 20  L13:   RETURN (0)   0
 21  L14:   LOADA        0[OB]
 22         LOADL        0
 23         LOAD         0[OB]
 24         LOADL        1
 25         CALL         add     
 26         CALL         fieldupd
 27         LOAD         0[OB]
 28         RETURN (1)   0
 29         LOADA        0[OB]
 30         RETURN (1)   0
 31  L15:   PUSH         1
 32         LOADL        -1
 33         LOADL        1
 34         CALL         newobj  
 35         STORE        3[LB]
 36         LOAD         3[LB]
 37         CALLI        L11
 38         LOAD         3[LB]
 39         LOADL        0
 40         CALL         fieldref
 41         CALL         L10
 42         RETURN (0)   1
