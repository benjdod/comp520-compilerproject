  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L16
  4         HALT   (0)   
  5         LOAD         -1[LB]
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
 34         JUMPIF (0)   L10
 35         LOAD         -1[LB]
 36         CALL         putarr  
 37         CALL         puteol  
 38         RETURN (0)   1
 39         LOAD         -2[LB]
 40         CALL         arraylen
 41         LOAD         -1[LB]
 42         CALL         arraylen
 43         CALL         ne      
 44         JUMPIF (0)   L13
 45  L12:   LOADL        0
 46         RETURN (1)   1
 47  L13:   LOAD         -1[LB]
 48         CALL         arraylen
 49         LOADL        0
 50  L14:   LOAD         4[LB]
 51         LOAD         3[LB]
 52         CALL         lt      
 53         JUMPIF (0)   L15
 54         LOAD         -2[LB]
 55         LOAD         4[LB]
 56         CALL         fieldref
 57         LOAD         -1[LB]
 58         LOAD         4[LB]
 59         CALL         fieldref
 60         CALL         eq      
 61         JUMPIF (0)   L12
 62         CALL         succ    
 63         JUMP         ***
 64  L15:   LOADL        1
 65         RETURN (1)   1
 66         LOAD         -1[LB]
 67         CALL         arraylen
 68         RETURN (1)   0
 69  L16:   PUSH         1
 70         LOADL        0
 71         STORE        3[LB]
 72         PUSH         1
 73         LOADL        20
 74         STORE        4[LB]
 75         JUMP         L18
 76  L17:   LOAD         3[LB]
 77         LOADL        1
 78         CALL         add     
 79         STORE        3[LB]
 80         LOAD         4[LB]
 81         LOADL        1
 82         CALL         sub     
 83         STORE        4[LB]
 84  L18:   LOAD         3[LB]
 85         LOAD         4[LB]
 86         CALL         le      
 87         JUMPIF (0)   L19
 88         LOADL        14
 89         STORE        3[LB]
 90         JUMP         L17
 91  L19:   POP          2
 92         RETURN (0)   1
