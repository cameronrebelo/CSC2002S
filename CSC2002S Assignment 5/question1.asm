.data
	startMessage: .asciiz "Enter a list of 3 lines:\n"
	endMessage:  .asciiz "The reordered list is:\n"
	userInput1: .space 20
	userInput2: .space 20
	userInput3: .space 20
.text
	main:
	
		li $v0, 4
		la $a0, startMessage
		syscall
		
		li $v0, 8 
		la $a0, userInput1
		li $a1, 20
		syscall
		
		li $v0, 8 
		la $a0, userInput2
		li $a1, 20
		syscall
		
		li $v0, 8 
		la $a0, userInput3
		li $a1, 20
		syscall
		
		li $v0, 4
		la $a0, endMessage
		syscall
		
		la $a0, userInput3
		li $v0, 4
		syscall
		
		la $a0, userInput1
		li $v0, 4		
		syscall
		
		la $a0, userInput2
		li $v0, 4
		syscall
 		
 	li $v0, 10
 	syscall
