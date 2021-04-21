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
 35  L15:   LOADL        5
 36         LOADL        5
 37         CALL         eq      
 38         LOAD         -1[ST]
 39         JUMPIF (1)   L16
 40         LOADL        5
 41         LOADL        0
 42         CALL         div     
 43         LOADL        9
 44         CALL         eq      
 45         CALL         or      
 46  L16:   JUMPIF (0)   L17
 47         LOADL        11111
 48         CALL         L10
 49  L17:   PUSH         1
 50         LOADL        -1
 51         LOADL        1
 52         CALL         newobj  
 53         STORE        3[LB]
 54         PUSH         1
 55         LOADL        -1
 56         LOADL        1
 57         CALL         newobj  
 58         STORE        4[LB]
 59         PUSH         1
 60         LOADL        -1
 61         LOADL        1
 62         CALL         newobj  
 63         STORE        5[LB]
 64         PUSH         1
 65         LOADL        0
 66         STORE        6[LB]
 67         LOAD         1[SB]
 68         LOADL        0
 69         CALL         eq      
 70         JUMPIF (0)   L18
 71         LOADL        1
 72         STORE        6[LB]
 73  L18:   LOAD         6[LB]
 74         CALL         L10
 75         LOAD         3[LB]
 76         CALLI        L11
 77         LOAD         3[LB]
 78         LOADL        0
 79         CALL         fieldref
 80         CALL         L10
 81         RETURN (0)   1
