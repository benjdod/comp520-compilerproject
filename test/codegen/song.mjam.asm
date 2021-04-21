  0         LOADL        0
  1         CALL         newarr  
  2         CALL         L13
  3         HALT   (0)   
  4         PUSH         1
  5         PUSH         1
  6  L10:   LOAD         -1[LB]
  7         CALL         putintnl
  8         RETURN (0)   1
  9  L11:   LOADA        0[OB]
 10         LOAD         1[SB]
 11         CALLI        L12
 12         RETURN (0)   0
 13  L12:   LOADL        1111
 14         CALL         neg     
 15         CALL         L10
 16         RETURN (0)   0
 17  L13:   LOADL        11111
 18         CALL         L10
 19         PUSH         1
 20         LOADL        -1
 21         LOADL        1
 22         CALL         newobj  
 23         STORE        3[LB]
 24         PUSH         1
 25         LOADL        -1
 26         LOADL        1
 27         CALL         newobj  
 28         STORE        4[LB]
 29         LOAD         3[LB]
 30         LOADL        0
 31         LOAD         4[LB]
 32         CALL         fieldupd
 33         PUSH         1
 34         LOADL        -1
 35         LOADL        0
 36         CALL         newobj  
 37         STORE        5[LB]
 38         LOAD         1[SB]
 39         LOADL        1
 40         CALL         fieldref
 41         CALL         L10
 42         LOAD         5[LB]
 43         LOAD         5[LB]
 44         CALLI        L11
 45         LOAD         3[LB]
 46         LOADL        0
 47         CALL         fieldref
 48         LOADL        0
 49         LOADL        15
 50         CALL         fieldupd
 51         LOAD         3[LB]
 52         LOADL        0
 53         CALL         fieldref
 54         LOADL        0
 55         LOAD         3[LB]
 56         LOADL        0
 57         CALL         fieldref
 58         LOADL        0
 59         CALL         fieldref
 60         LOADL        15
 61         CALL         add     
 62         LOAD         4[LB]
 63         LOADL        0
 64         CALL         fieldref
 65         LOADL        7
 66         CALL         mult    
 67         CALL         sub     
 68         CALL         fieldupd
 69         LOAD         3[LB]
 70         STORE        3[LB]
 71         LOAD         4[LB]
 72         STORE        4[LB]
 73         LOAD         3[LB]
 74         LOADL        0
 75         CALL         fieldref
 76         CALLI        L14
 77         CALL         L10
 78         LOADL        99999
 79         CALL         L10
 80         RETURN (0)   1
 81  L14:   LOAD         0[OB]
 82         RETURN (1)   0
