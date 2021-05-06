  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L17
  4         HALT   (0)   
  5  L10:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8         CALL         puteol  
  9         RETURN (0)   0
 10         LOAD         -1[LB]
 11         JUMPIF (0)   L11
 12         LOADL        116
 13         CALL         put     
 14         LOADL        114
 15         CALL         put     
 16         LOADL        117
 17         CALL         put     
 18         LOADL        101
 19         CALL         put     
 20         RETURN (0)   1
 21  L11:   LOADL        102
 22         CALL         put     
 23         LOADL        97
 24         CALL         put     
 25         LOADL        108
 26         CALL         put     
 27         LOADL        115
 28         CALL         put     
 29         LOADL        101
 30         CALL         put     
 31         CALL         puteol  
 32         RETURN (0)   1
 33         LOAD         -1[LB]
 34         JUMPIF (0)   L12
 35         LOAD         -1[LB]
 36         CALL         putarr  
 37         CALL         puteol  
 38         RETURN (0)   1
 39  L12:   LOADL        110
 40         CALL         put     
 41         LOADL        117
 42         CALL         put     
 43         LOADL        108
 44         CALL         put     
 45         LOADL        108
 46         CALL         put     
 47         CALL         puteol  
 48         RETURN (0)   1
 49         LOAD         -2[LB]
 50         CALL         arraylen
 51         LOAD         -1[LB]
 52         CALL         arraylen
 53         CALL         ne      
 54         JUMPIF (0)   L14
 55  L13:   LOADL        0
 56         RETURN (1)   1
 57  L14:   LOAD         -1[LB]
 58         CALL         arraylen
 59         LOADL        0
 60  L15:   LOAD         4[LB]
 61         LOAD         3[LB]
 62         CALL         lt      
 63         JUMPIF (0)   L16
 64         LOAD         -2[LB]
 65         LOAD         4[LB]
 66         CALL         fieldref
 67         LOAD         -1[LB]
 68         LOAD         4[LB]
 69         CALL         fieldref
 70         CALL         eq      
 71         JUMPIF (0)   L13
 72         CALL         succ    
 73         JUMP         ***
 74  L16:   LOADL        1
 75         RETURN (1)   1
 76         LOAD         -1[LB]
 77         CALL         arraylen
 78         RETURN (1)   0
 79  L17:   PUSH         1
 80         LOADL        0
 81         STORE        3[LB]
 82         PUSH         1
 83         LOADL        0
 84         STORE        4[LB]
 85         JUMP         L18
 86  L18:   LOAD         4[LB]
 87         LOADL        5
 88         CALL         ne      
 89         JUMPIF (0)   L22
 90         LOAD         3[LB]
 91         JUMPIF (0)   L20
 92         JUMP         L22
 93  L19:   JUMP         L21
 94  L20:   LOAD         4[LB]
 95         LOADL        1
 96         CALL         add     
 97         STORE        4[LB]
 98  L21:   JUMP         L18
 99  L22:   POP          0
100         LOAD         4[LB]
101         CALL         L10
102         RETURN (0)   1
