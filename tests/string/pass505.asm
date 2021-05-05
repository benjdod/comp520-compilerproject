  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L13
  4         HALT   (0)   
  5         LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8         CALL         puteol  
  9         RETURN (0)   0
 10         LOAD         -1[LB]
 11         JUMPIF (0)   L10
 12         LOADL        116
 13         CALL         put     
 14         LOADL        114
 15         CALL         put     
 16         LOADL        117
 17         CALL         put     
 18         LOADL        101
 19         CALL         put     
 20         RETURN (0)   1
 21  L10:   LOADL        102
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
 33  L11:   LOAD         -1[LB]
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
 49  L13:   PUSH         1
 50         LOADL        -1
 51         LOADL        2
 52         CALL         newobj  
 53         STORE        3[LB]
 54         LOADL        3
 55         CALL         newarr  
 56         LOAD         -1[ST]
 57         LOADL        0
 58         LOADL        45
 59         CALL         arrayupd
 60         LOAD         -1[ST]
 61         LOADL        1
 62         LOADL        95
 63         CALL         arrayupd
 64         LOAD         -1[ST]
 65         LOADL        2
 66         LOADL        45
 67         CALL         arrayupd
 68         LOAD         3[LB]
 69         CALLI        L14
 70         LOADL        10
 71         LOAD         3[LB]
 72         CALLI        L15
 73         LOAD         3[LB]
 74         CALLI        L16
 75         RETURN (0)   1
 76  L14:   LOADA        0[OB]
 77         LOADL        0
 78         LOAD         -1[LB]
 79         CALL         fieldupd
 80         RETURN (0)   1
 81  L15:   LOADA        0[OB]
 82         LOADL        1
 83         LOAD         -1[LB]
 84         CALL         fieldupd
 85         RETURN (0)   1
 86         LOADA        0[OB]
 87         LOADL        0
 88         CALL         fieldref
 89         RETURN (1)   0
 90  L16:   PUSH         1
 91         LOADL        0
 92         STORE        3[LB]
 93  L17:   LOAD         3[LB]
 94         LOAD         1[OB]
 95         CALL         lt      
 96         JUMPIF (0)   L18
 97         LOADA        0[OB]
 98         LOADL        0
 99         CALL         fieldref
100         CALL         L11
101         LOAD         3[LB]
102         LOADL        1
103         CALL         add     
104         STORE        3[LB]
105         JUMP         L17
106  L18:   RETURN (0)   0
