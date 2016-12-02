from tkinter import *

from board import PlayerOptions, MatrixValues

class UI:
    def __init__(self, board):
        self.board = board
        self.wait_for_player = False
        
        # create the main window
        self.master = Tk(className='Othello')
        self.frame = Frame(self.master)
        self.frame.grid(row=0,column=0)

        # set the board matrix
        self.buttons = []
        for i in range(12):
            for j in range(12):
                # button = Button(self.frame, text="      ", command= lambda x=i, y=j: self.player_action(x,y))			
                button = Button(self.frame, text='{:3d}'.format(i * 12 + j), command= lambda x=i, y=j: self.player_action(x,y))			
                button.grid(column=j,row=i)	
                self.buttons.append(button)	
        
        # set the options menu
        self.players = [PlayerOptions.Player, PlayerOptions.Player]
        self.player_buttons = []
        label_player_1 = Label(self.frame, text="Toggle Player 1")
        label_player_1.grid(column=12,row=0)	
        player_1_button = Button(self.frame, text=self.players[0].name, command=lambda x=0: self.toggle(x))
        player_1_button.grid(column=12,row=1)
        self.player_buttons.append(player_1_button)

        label_player_2 = Label(self.frame, text="Toggle Player 2")
        label_player_2.grid(column=12,row=2)	
        player_2_button = Button(self.frame, text=self.players[1].name, command=lambda x=1: self.toggle(x))
        player_2_button.grid(column=12,row=3)	
        self.player_buttons.append(player_2_button)
        
        self.board.turn_loop(self)
        
    def toggle(self, player_num):
        # a callback for clicking one of the playes selection toggles 

        # cycle through the possible players
        new = PlayerOptions.get_next(self.players[player_num])
        self.players[player_num] = new
        self.player_buttons[player_num].config(text=new.name)

    def loop(self):
        # call the framework main loop
        self.master.mainloop()
    
    def player_action(self,x,y):
        # a callback for clicking the matrix
        if self.wait_for_player:
            index = 12 * x + y
            print('index', index)
            if self.board.state.matrix[index] == MatrixValues.neighbor:
                self.board.state.matrix[index] = MatrixValues(self.board.state.turn + 1)
                self.wait_for_player = False
                self.board.next_turn()
                self.board.clear_neighbors()
                self.draw_board()
            # self.buttons[12 * x + y].config(bg="red")
        
    def draw_board(self):
        # draw the current state of the board
        colors = {MatrixValues.empty: 'gray', 
	              MatrixValues.player_1: 'white',
                  MatrixValues.player_2: 'black',
                  MatrixValues.neighbor: 'yellow'}
        for i in range(144):
            self.buttons[i].config(bg=colors[self.board.state.matrix[i]])
    