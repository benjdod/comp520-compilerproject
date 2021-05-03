  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L14
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
 20         CALL         puteol  
 21         RETURN (0)   1
 22  L10:   LOADL        102
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
 34  L11:   PUSH         1
 35         LOADL        0
 36         LOAD         -1[LB]
 37         CALL         arraylen
 38         STORE        3[LB]
 39  L12:   LOAD         3[LB]
 40         LOAD         4[LB]
 41         CALL         sub     
 42         JUMPIF (0)   L13
 43         LOAD         -1[LB]
 44         LOAD         4[LB]
 45         CALL         arrayref
 46         CALL         put     
 47         CALL         succ    
 48         JUMP         L12
 49  L13:   CALL         puteol  
 50         RETURN (0)   1
 51  L14:   PUSH         1
 52         LOADL        13
 53         CALL         newarr  
 54         LOAD         -1[ST]
 55         LOADL        0
 56         LOADL        104
 57         CALL         arrayupd
 58         LOAD         -1[ST]
 59         LOADL        1
 60         LOADL        101
 61         CALL         arrayupd
 62         LOAD         -1[ST]
 63         LOADL        2
 64         LOADL        108
 65         CALL         arrayupd
 66         LOAD         -1[ST]
 67         LOADL        3
 68         LOADL        108
 69         CALL         arrayupd
 70         LOAD         -1[ST]
 71         LOADL        4
 72         LOADL        111
 73         CALL         arrayupd
 74         LOAD         -1[ST]
 75         LOADL        5
 76         LOADL        32
 77         CALL         arrayupd
 78         LOAD         -1[ST]
 79         LOADL        6
 80         LOADL        92
 81         CALL         arrayupd
 82         LOAD         -1[ST]
 83         LOADL        7
 84         LOADL        32
 85         CALL         arrayupd
 86         LOAD         -1[ST]
 87         LOADL        8
 88         LOADL        116
 89         CALL         arrayupd
 90         LOAD         -1[ST]
 91         LOADL        9
 92         LOADL        104
 93         CALL         arrayupd
 94         LOAD         -1[ST]
 95         LOADL        10
 96         LOADL        101
 97         CALL         arrayupd
 98         LOAD         -1[ST]
 99         LOADL        11
100         LOADL        114
101         CALL         arrayupd
102         LOAD         -1[ST]
103         LOADL        12
104         LOADL        101
105         CALL         arrayupd
106         STORE        3[LB]
107         LOAD         3[LB]
108         CALL         L11
109         PUSH         1
110         LOAD         3[LB]
111         STORE        4[LB]
112         LOAD         4[LB]
113         CALL         L11
114         RETURN (0)   1
