.data
test: .asciiz "abcdef"
userInput: .space 20
equals: .asciiz "="
addition: .asciiz "+"
subtraction: .asciiz "-"
posMes: .asciiz "Pos succes"

.text
main: 
	la $a0, userInput
	li $a1, 20
	li $v0, 8
	syscall
	
	la $t4, equals
	lb $s1, 0($t4)
	la $t4, addition
	lb $s2, 0($t4)
	la $t4, subtraction
	lb $s3, 0($t4)

	la $t1, userInput
	li $t0,0
	li $t7, 0
loop:
	add $t3, $t1,$t0
	lb $t6, 0($t3)
	beq $t6, $s1, exit
	beq $t6, $s2, positive
	beq $t6, $s3, negative
	bge $t6, $zero, number
	addi $t0,$t0,1
	syscall
j loop

exit:
la $a0, ($t7)
li $v0, 1
syscall
li $v0, 10
syscall

positive: 
	addi $t0,$t0,1
	add $t3, $t1,$t0
	lb $t6, 0($t3)
	add $t6,$t6,-48
	add $t7, $t7, $t6 

	j loop
negative:
	addi $t0,$t0,1
	add $t3, $t1,$t0
	lb $t6, 0($t3)
	add $t6,$t6,-48
	sub $t7, $t7, $t6 
	
	j loop
number:
	beq $t0,$zero,firstDigit
	addi $t0,$t0,1
	j loop
firstDigit:
	add $t3, $t1,$t0
	lb $t6, 0($t3)
	add $t6,$t6,-48
	add $t7, $t7, $t6 
	addi $t0,$t0,1
	
	j loop