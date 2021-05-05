  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L16
  4         HALT   (0)   
  5  L11:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8  L12:   CALL         puteol  
  9         RETURN (0)   0
 10  L13:   LOAD         -1[LB]
 11         JUMPIF (0)   L14
 12         LOADL        116
 13         CALL         put     
 14         LOADL        114
 15         CALL         put     
 16         LOADL        117
 17         CALL         put     
 18         LOADL        101
 19         CALL         put     
 20         RETURN (0)   1
 21  L14:   LOADL        102
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
 33  L15:   LOAD         -1[LB]
 34         JUMPIF (0)   L10
 35         LOAD         -1[LB]
 36         CALL         putarr  
 37         CALL         puteol  
 38         RETURN (0)   1
 39  L16:   LOADL        6
 40         CALL         newarr  
 41         LOAD         -1[ST]
 42         LOADL        0
 43         LOADL        98
 44         CALL         arrayupd
 45         LOAD         -1[ST]
 46         LOADL        1
 47         LOADL        101
 48         CALL         arrayupd
 49         LOAD         -1[ST]
 50         LOADL        2
 51         LOADL        103
 52         CALL         arrayupd
 53         LOAD         -1[ST]
 54         LOADL        3
 55         LOADL        105
 56         CALL         arrayupd
 57         LOAD         -1[ST]
 58         LOADL        4
 59         LOADL        110
 60         CALL         arrayupd
 61         LOAD         -1[ST]
 62         LOADL        5
 63         LOADL        33
 64         CALL         arrayupd
 65         CALL         L15
 66         CALL         L12
 67         CALL         L12
 68         CALL         L12
 69         LOADL        0
 70         CALL         L13
 71         LOADL        4
 72         CALL         L11
 73         LOADL        1
 74         CALL         L13
 75         CALL         L12
 76         CALL         L12
 77         CALL         L12
 78         CALL         L12
 79         LOADL        4
 80         CALL         newarr  
 81         LOAD         -1[ST]
 82         LOADL        0
 83         LOADL        101
 84         CALL         arrayupd
 85         LOAD         -1[ST]
 86         LOADL        1
 87         LOADL        110
 88         CALL         arrayupd
 89         LOAD         -1[ST]
 90         LOADL        2
 91         LOADL        100
 92         CALL         arrayupd
 93         LOAD         -1[ST]
 94         LOADL        3
 95         LOADL        33
 96         CALL         arrayupd
 97         CALL         L15
 98         RETURN (0)   1
