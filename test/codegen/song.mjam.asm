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
 17  L13:   LOADL        10101
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
 38         LOAD         5[LB]
 39         LOAD         5[LB]
 40         CALLI        L11
 41         LOAD         3[LB]
 42         LOADL        0
 43         CALL         fieldref
 44         LOADL        0
 45         LOADL        15
 46         CALL         fieldupd
 47         LOAD         3[LB]
 48         LOADL        0
 49         CALL         fieldref
 50         LOADL        0
 51         LOAD         3[LB]
 52         LOADL        0
 53         CALL         fieldref
 54         LOADL        0
 55         CALL         fieldref
 56         LOADL        15
 57         CALL         add     
 58         LOAD         4[LB]
 59         LOADL        0
 60         CALL         fieldref
 61         LOADL        7
 62         CALL         mult    
 63         CALL         sub     
 64         CALL         fieldupd
 65         LOAD         3[LB]
 66         STORE        3[LB]
 67         LOAD         4[LB]
 68         STORE        4[LB]
 69         LOAD         4[LB]
 70         LOAD         3[LB]
 71         LOAD         4[LB]
 72         LOAD         4[LB]
 73         CALLI        L15
 74         POP          0
 75         LOAD         4[LB]
 76         LOADL        0
 77         LOAD         4[LB]
 78         CALLI        L15
 79         CALL         L10
 80         LOAD         3[LB]
 81         LOADL        0
 82         CALL         fieldref
 83         CALLI        L14
 84         CALL         L10
 85         LOADL        90909
 86         CALL         L10
 87         RETURN (0)   1
 88  L14:   LOAD         0[OB]
 89         RETURN (1)   0
 90  L15:   PUSH         1
 91         LOADL        0
 92         STORE        3[LB]
 93         LOAD         -1[LB]
 94         LOADL        0
 95         CALL         ne      
 96         JUMPIF (0)   L16
 97         LOADL        1
 98         STORE        3[LB]
 99  L16:   LOAD         3[LB]
100         LOADA        0[OB]
101         LOADL        0
102         CALL         fieldref
103         CALL         add     
104         LOAD         -2[LB]
105         LOADL        0
106         CALL         fieldref
107         CALL         add     
108         RETURN (1)   2
