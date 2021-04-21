  0         LOADL        0
  1         CALL         newarr  
  2         CALL         L11
  3         HALT   (0)   
  4         PUSH         1
  5  L10:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8         RETURN (0)   0
  9  L11:   LOADL        2
 10         CALL         L10
 11         PUSH         1
 12         LOADL        -1
 13         LOADL        1
 14         CALL         newobj  
 15         STORE        3[LB]
 16         PUSH         1
 17         LOADL        -1
 18         LOADL        1
 19         CALL         newobj  
 20         STORE        4[LB]
 21         LOAD         3[LB]
 22         LOADL        0
 23         LOAD         4[LB]
 24         CALL         fieldupd
 25         PUSH         1
 26         LOAD         3[LB]
 27         LOADL        0
 28         CALL         fieldref
 29         CALLI        L12
 30         STORE        5[LB]
 31         LOAD         3[LB]
 32         LOADL        0
 33         CALL         fieldref
 34         CALLI        L12
 35         CALL         L10
 36         LOAD         3[LB]
 37         LOADL        0
 38         CALL         fieldref
 39         LOADL        0
 40         LOADL        15
 41         CALL         fieldupd
 42         LOAD         3[LB]
 43         LOADL        0
 44         CALL         fieldref
 45         LOADL        0
 46         LOAD         3[LB]
 47         LOADL        0
 48         CALL         fieldref
 49         LOADL        0
 50         CALL         fieldref
 51         LOADL        15
 52         CALL         add     
 53         LOAD         4[LB]
 54         LOADL        0
 55         CALL         fieldref
 56         LOADL        7
 57         CALL         mult    
 58         CALL         sub     
 59         CALL         fieldupd
 60         LOAD         3[LB]
 61         STORE        3[LB]
 62         LOAD         4[LB]
 63         STORE        4[LB]
 64         LOAD         3[LB]
 65         LOADL        0
 66         CALL         fieldref
 67         CALLI        L12
 68         CALL         L10
 69         RETURN (0)   1
 70  L12:   LOAD         0[OB]
 71         RETURN (1)   0
