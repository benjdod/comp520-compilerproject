  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L17
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
 33  L12:   LOAD         -1[LB]
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
 44         JUMPIF (0)   L14
 45  L13:   LOADL        0
 46         RETURN (1)   1
 47  L14:   LOAD         -1[LB]
 48         CALL         arraylen
 49         LOADL        0
 50  L15:   LOAD         4[LB]
 51         LOAD         3[LB]
 52         CALL         lt      
 53         JUMPIF (0)   L16
 54         LOAD         -2[LB]
 55         LOAD         4[LB]
 56         CALL         fieldref
 57         LOAD         -1[LB]
 58         LOAD         4[LB]
 59         CALL         fieldref
 60         CALL         eq      
 61         JUMPIF (0)   L13
 62         CALL         succ    
 63         JUMP         ***
 64  L16:   LOADL        1
 65         RETURN (1)   1
 66         LOAD         -1[LB]
 67         CALL         arraylen
 68         RETURN (1)   0
 69  L17:   LOADL        5
 70         CALL         newarr  
 71         LOAD         -1[ST]
 72         LOADL        0
 73         LOADL        104
 74         CALL         arrayupd
 75         LOAD         -1[ST]
 76         LOADL        1
 77         LOADL        101
 78         CALL         arrayupd
 79         LOAD         -1[ST]
 80         LOADL        2
 81         LOADL        108
 82         CALL         arrayupd
 83         LOAD         -1[ST]
 84         LOADL        3
 85         LOADL        108
 86         CALL         arrayupd
 87         LOAD         -1[ST]
 88         LOADL        4
 89         LOADL        111
 90         CALL         arrayupd
 91         CALL         L12
 92         LOADL        8
 93         CALL         newarr  
 94         LOAD         -1[ST]
 95         LOADL        0
 96         LOADL        121
 97         CALL         arrayupd
 98         LOAD         -1[ST]
 99         LOADL        1
100         LOADL        111
101         CALL         arrayupd
102         LOAD         -1[ST]
103         LOADL        2
104         LOADL        117
105         CALL         arrayupd
106         LOAD         -1[ST]
107         LOADL        3
108         LOADL        32
109         CALL         arrayupd
110         LOAD         -1[ST]
111         LOADL        4
112         LOADL        115
113         CALL         arrayupd
114         LOAD         -1[ST]
115         LOADL        5
116         LOADL        117
117         CALL         arrayupd
118         LOAD         -1[ST]
119         LOADL        6
120         LOADL        99
121         CALL         arrayupd
122         LOAD         -1[ST]
123         LOADL        7
124         LOADL        107
125         CALL         arrayupd
126         CALL         L12
127         RETURN (0)   1
