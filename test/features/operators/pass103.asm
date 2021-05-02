  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L12
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
 20         CALL         puteol  
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
 34  L12:   PUSH         1
 35         LOADL        6
 36         STORE        3[LB]
 37         PUSH         1
 38         LOAD         3[LB]
 39         LOADL        8
 40         CALL         lt      
 41         JUMPIF (0)   L17
 42         LOAD         3[LB]
 43         LOADL        4
 44         CALL         gt      
 45         JUMPIF (0)   L15
 46         LOAD         3[LB]
 47         LOADL        6
 48         CALL         le      
 49         JUMPIF (0)   L13
 50         LOAD         3[LB]
 51         LOADL        1
 52         CALL         add     
 53         JUMP         L14
 54  L13:   LOADL        10
 55  L14:   JUMP         L16
 56  L15:   LOADL        0
 57  L16:   JUMP         L19
 58  L17:   LOAD         3[LB]
 59         LOADL        5
 60         CALL         neg     
 61         CALL         lt      
 62         JUMPIF (0)   L18
 63         LOADL        6
 64         JUMP         L19
 65  L18:   LOADL        2700
 66  L19:   STORE        4[LB]
 67         LOAD         4[LB]
 68         CALL         L10
 69         RETURN (0)   1
