;I pledge that this submission is solely my work, and that I have neither given, nor received help from anyone        
;Jonathan Rescorla        
;49982379        
        segment .data                       ;declaring data
num     db    38  
ind     db     0
size    db    10
array   dd    45, 15, 21, 38, 12, 8, 58, 29, 1, 88

        segment .text
        global main
main:
        xor rax, rax                        ;preparing registers
        xor rbx, rbx
        xor rcx, rcx
        xor rdx, rdx
        xor r9, r9
        xor r10, r10
        xor r11, r11           
        movsx r11, byte [size]              ;moving final variable [size] to r11
        dec r11                             ;subtracting one from size to get accurate array index representation
        jmp bubblesort                      ;jumping to bubblesort loop
bubblesort:
        lea rbx, [array + 4 + (4 * r9)]     ;loading the 2nd array position into rbx
        movsxd rax, dword[array + r9 * 4]   ;loading the 1st array postion into rax
        mov ecx, [rbx]                      ;moving rbx into rcx for later comparison
        cmp rcx, rax                        ;comparing rcx with rax
        cmovl rdx, rcx                      ;transferring contents into rdx and r8 to sort
        cmovl r8, rax
        cmovg rdx, rax                      ;transferring contents into rdx and r8 to continue through the array
        cmovg r8, rcx
        mov [array + r9 * 4], edx           ;supplanting element at index r9 with the contents of rcx 
        inc r9                              ;incrementing counter
        mov [array + r9 * 4], r8d           ;supplanting element at index r9 (now r9 + 1) with the contents of r8
        cmp rcx, rax                        ;repeated comparison to restart the sort
        cmovl r9, r10                       ;conditionally clearing r9 (index) with r10 (zero)
        cmp r9, r11                         ;comparing our index with the (size - 1) of the array
        jz binarysearch                     ;exiting the loop if zero flag is tripped
        jmp bubblesort                      ;restarting the loop
binarysearch:
        movsx rbx, byte[num]                ;moving our target into rbx
        movzx rcx, byte[ind]                ;moving our index counter into rcx
        movsxd rax, dword[array + 4 * rcx]  ;moving each iterated element of the array into rax
        inc rcx                             ;incrementing our counter
        cmp rax, rbx                        ;comparing rax to the target    
        jz exit                             ;exiting if our target is found
        mov [ind], cl                       ;else, move our incremented index into [ind] in memory
        jmp binarysearch                    ;restarting the loop
exit:
        xor rax, rax                        ;clearing registers and returning
        xor rbx, rbx
        xor rcx, rcx
        xor rdx, rdx
        xor r8, r8
        xor r9, r9
        xor r10, r10
        xor r11, r11
        ret

