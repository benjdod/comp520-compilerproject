  0         PUSH         1
  1         PUSH         1
  2         LOADL        0
  3         CALL         newarr  
  4         CALL         L21
  5         HALT   (0)   
  6  L10:   LOAD         -1[LB]
  7         CALL         putintnl
  8         RETURN (0)   1
  9         CALL         puteol  
 10         RETURN (0)   0
 11         LOAD         -1[LB]
 12         JUMPIF (0)   L11
 13         LOADL        116
 14         CALL         put     
 15         LOADL        114
 16         CALL         put     
 17         LOADL        117
 18         CALL         put     
 19         LOADL        101
 20         CALL         put     
 21         RETURN (0)   1
 22  L11:   LOADL        102
 23         CALL         put     
 24         LOADL        97
 25         CALL         put     
 26         LOADL        108
 27         CALL         put     
 28         LOADL        115
 29         CALL         put     
 30         LOADL        101
 31         CALL         put     
 32         CALL         puteol  
 33         RETURN (0)   1
 34         LOAD         -1[LB]
 35         JUMPIF (0)   L12
 36         LOAD         -1[LB]
 37         CALL         putarr  
 38         CALL         puteol  
 39         RETURN (0)   1
 40  L12:   LOADL        110
 41         CALL         put     
 42         LOADL        117
 43         CALL         put     
 44         LOADL        108
 45         CALL         put     
 46         LOADL        108
 47         CALL         put     
 48         CALL         puteol  
 49         RETURN (0)   1
 50         LOAD         -2[LB]
 51         CALL         arraylen
 52         LOAD         -1[LB]
 53         CALL         arraylen
 54         CALL         ne      
 55         JUMPIF (0)   L14
 56  L13:   LOADL        0
 57         RETURN (1)   1
 58  L14:   LOAD         -1[LB]
 59         CALL         arraylen
 60         LOADL        0
 61  L15:   LOAD         4[LB]
 62         LOAD         3[LB]
 63         CALL         lt      
 64         JUMPIF (0)   L16
 65         LOAD         -2[LB]
 66         LOAD         4[LB]
 67         CALL         fieldref
 68         LOAD         -1[LB]
 69         LOAD         4[LB]
 70         CALL         fieldref
 71         CALL         eq      
 72         JUMPIF (0)   L13
 73         CALL         succ    
 74         JUMP         ***
 75  L16:   LOADL        1
 76         RETURN (1)   1
 77         LOAD         -1[LB]
 78         CALL         arraylen
 79         RETURN (1)   0
 80  L17:   LOAD         1[SB]
 81         CALL         not     
 82         STORE        1[SB]
 83         LOAD         1[SB]
 84         RETURN (1)   0
 85  L18:   CALL         L17
 86         JUMPIF (0)   L19
 87         LOADL        2
 88         JUMP         L20
 89  L19:   LOADL        5
 90         CALL         neg     
 91  L20:   RETURN (1)   0
 92  L21:   CALL         L18
 93         POP          1
 94         CALL         L18
 95         POP          1
 96         CALL         L18
 97         CALL         L10
 98         RETURN (0)   1
