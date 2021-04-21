  0         LOADL        0
  1         CALL         newarr  
  2         CALL         L15
  3         HALT   (0)   
  4         PUSH         1
  5         PUSH         1
  6  L10:   LOAD         -1[LB]
  7         CALL         putintnl
  8         RETURN (0)   1
  9  L11:   LOADL        3
 10         STORE        0[OB]
 11  L12:   LOAD         0[OB]
 12         LOADL        10
 13         CALL         lt      
 14         JUMPIF (0)   L13
 15         PUSH         1
 16         LOADA        0[OB]
 17         CALLI        L14
 18         STORE        3[LB]
 19         POP          1
 20         JUMP         L12
 21  L13:   RETURN (0)   0
 22  L14:   LOADA        0[OB]
 23         LOADL        0
 24         LOAD         0[OB]
 25         LOADL        1
 26         CALL         add     
 27         CALL         fieldupd
 28         LOAD         0[OB]
 29         RETURN (1)   0
 30         LOADA        0[OB]
 31         RETURN (1)   0
 32  L15:   PUSH         1
 33         LOADL        -1
 34         LOADL        1
 35         CALL         newobj  
 36         STORE        3[LB]
 37         LOAD         1[SB]
 38         LOADL        1
 39         LOADL        3
 40         CALL         fieldupd
 41         LOAD         3[LB]
 42         CALLI        L11
 43         LOAD         3[LB]
 44         LOADL        0
 45         CALL         fieldref
 46         CALL         L10
 47         RETURN (0)   1
