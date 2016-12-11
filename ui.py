from tkinter import *

from board import PlayerOptions
from enums import *

class UI:
    def __init__(self, board):
        self.board = board
        self.winner = None
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
                button.grid(column=j, row=i)	
                self.buttons.append(button)	
        
        # set the options menu
        self.players = [PlayerOptions.Player, PlayerOptions.Player]
        self.player_buttons = []
        label_player_1 = Label(self.frame, text="Toggle Player 1")
        label_player_1.grid(column=12, row=0)	
        player_1_button = Button(self.frame, text=self.players[0].name, command=lambda x=0: self.toggle(x))
        player_1_button.grid(column=12, row=1)
        self.player_buttons.append(player_1_button)

        label_player_2 = Label(self.frame, text="Toggle Player 2")
        label_player_2.grid(column=12, row=2)	
        player_2_button = Button(self.frame, text=self.players[1].name, command=lambda x=1: self.toggle(x))
        player_2_button.grid(column=12, row=3)
        self.player_buttons.append(player_2_button)
        
        label_reset = Label(self.frame, text="Reset the game")
        label_reset.grid(column=12, row=4)
        reset_button = Button(self.frame, text="Reset", command=self.reset)
        reset_button.grid(column=12, row=5)
        
        label_depth_text = Label(self.frame, text="change heuristic funcion depth")
        label_depth_text.grid(column=12, row=6)
        label_depth = Label(self.frame, text=str(self.board.max_depth))
        label_depth.grid(column=12, row=8)
        increase_depth_button = Button(self.frame, text=" /\ ", command=lambda label=label_depth: self.increase_depth(label))
        increase_depth_button.grid(column=12, row=7)
        decrease_depth_button = Button(self.frame, text=" \/ ", command=lambda label=label_depth: self.decrease_depth(label))
        decrease_depth_button.grid(column=12, row=9)

        # placeholder for the winner label
        self.label_winner = None
        
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
                self.board.player_action(index, self)
                self.wait_for_player = False
                self.board.turn_loop(self)
            # self.buttons[12 * x + y].config(bg="red")
        
    def draw_board(self):
        # draw the current state of the board
        colors = {MatrixValues.empty: 'gray', 
	              MatrixValues.player_1: 'white',
                  MatrixValues.player_2: 'black',
                  MatrixValues.neighbor: 'yellow'}
        for i in range(144):
            self.buttons[i].config(bg=colors[self.board.state.matrix[i]])
        if self.winner is not None:
           # print winner here...
           self.label_winner = Label(self.frame, text=str(self.winner.name + ' WINS!')) 
           self.label_winner.grid(column=3, row=3, columnspan=6, rowspan=6)	
        self.master.update_idletasks()
	
    def reset(self):
        if self.label_winner is not None:
            self.label_winner.grid_remove()
        self.board.reset(self)
      
    def increase_depth(self, label):
        self.board.max_depth += 1
        label.config(text=str(self.board.max_depth))

    def decrease_depth(self, label):
        if self.board.max_depth > 1:
            self.board.max_depth -= 1
            label.config(text=str(self.board.max_depth))
   
