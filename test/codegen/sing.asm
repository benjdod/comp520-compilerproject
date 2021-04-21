  0         PUSH         1
  1         PUSH         1
  2         LOADL        0
  3         CALL         newarr  
  4         CALL         L15
  5         HALT   (0)   
  6  L10:   LOAD         -1[LB]
  7         CALL         putintnl
  8         RETURN (0)   1
  9         LOADL        6642
 10         CALL         L10
 11         RETURN (0)   0
 12  L11:   LOADL        3
 13         STORE        0[OB]
 14  L12:   LOAD         0[OB]
 15         LOADL        10
 16         CALL         lt      
 17         JUMPIF (0)   L13
 18         PUSH         1
 19         LOADA        0[OB]
 20         CALLI        L14
 21         STORE        3[LB]
 22         POP          1
 23         JUMP         L12
 24  L13:   RETURN (0)   0
 25  L14:   LOADA        0[OB]
 26         LOADL        0
 27         LOAD         0[OB]
 28         LOADL        1
 29         CALL         add     
 30         CALL         fieldupd
 31         LOAD         0[OB]
 32         RETURN (1)   0
 33         LOADA        0[OB]
 34         RETURN (1)   0
 35  L15:   PUSH         1
 36         LOADL        -1
 37         LOADL        1
 38         CALL         newobj  
 39         STORE        3[LB]
 40         PUSH         1
 41         LOADL        -1
 42         LOADL        1
 43         CALL         newobj  
 44         STORE        4[LB]
 45         PUSH         1
 46         LOADL        -1
 47         LOADL        1
 48         CALL         newobj  
 49         STORE        5[LB]
 50         PUSH         1
 51         LOADL        0
 52         STORE        6[LB]
 53         LOAD         1[SB]
 54         LOADL        0
 55         CALL         eq      
 56         JUMPIF (0)   L16
 57         LOADL        1
 58         STORE        6[LB]
 59  L16:   LOAD         6[LB]
 60         CALL         L10
 61         LOAD         3[LB]
 62         CALLI        L11
 63         LOAD         3[LB]
 64         LOADL        0
 65         CALL         fieldref
 66         CALL         L10
 67         RETURN (0)   1
