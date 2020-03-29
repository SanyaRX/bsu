namespace ImageFilters
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.sourcePictureBox = new System.Windows.Forms.PictureBox();
            this.resultPictureBox = new System.Windows.Forms.PictureBox();
            this.label1 = new System.Windows.Forms.Label();
            this.chooseButton = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.transformButton = new System.Windows.Forms.Button();
            this.methodMenuStrip = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.addConstantMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.multiplyByConstantMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toNegativeMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.powerTransformMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.logarithmicTransformMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.adoptiveMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.BernsenTransform = new System.Windows.Forms.ToolStripMenuItem();
            this.OtsuTransform = new System.Windows.Forms.ToolStripMenuItem();
            this.NiblackTransforms = new System.Windows.Forms.ToolStripMenuItem();
            this.paramsTextBox = new System.Windows.Forms.TextBox();
            this.openSourceDialog = new System.Windows.Forms.OpenFileDialog();
            this.label3 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.sourcePictureBox)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.resultPictureBox)).BeginInit();
            this.methodMenuStrip.SuspendLayout();
            this.SuspendLayout();
            // 
            // sourcePictureBox
            // 
            this.sourcePictureBox.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.sourcePictureBox.Location = new System.Drawing.Point(11, 62);
            this.sourcePictureBox.Margin = new System.Windows.Forms.Padding(4);
            this.sourcePictureBox.Name = "sourcePictureBox";
            this.sourcePictureBox.Size = new System.Drawing.Size(515, 445);
            this.sourcePictureBox.TabIndex = 0;
            this.sourcePictureBox.TabStop = false;
            // 
            // resultPictureBox
            // 
            this.resultPictureBox.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.resultPictureBox.Location = new System.Drawing.Point(631, 62);
            this.resultPictureBox.Margin = new System.Windows.Forms.Padding(4);
            this.resultPictureBox.Name = "resultPictureBox";
            this.resultPictureBox.Size = new System.Drawing.Size(515, 445);
            this.resultPictureBox.TabIndex = 1;
            this.resultPictureBox.TabStop = false;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(15, 37);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(95, 17);
            this.label1.TabIndex = 2;
            this.label1.Text = "Source image";
            // 
            // chooseButton
            // 
            this.chooseButton.Location = new System.Drawing.Point(116, 33);
            this.chooseButton.Margin = new System.Windows.Forms.Padding(4);
            this.chooseButton.Name = "chooseButton";
            this.chooseButton.Size = new System.Drawing.Size(85, 25);
            this.chooseButton.TabIndex = 3;
            this.chooseButton.Text = "Choose...";
            this.chooseButton.UseVisualStyleBackColor = true;
            this.chooseButton.Click += new System.EventHandler(this.chooseButton_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(635, 42);
            this.label2.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(131, 17);
            this.label2.TabIndex = 4;
            this.label2.Text = "Transformed image";
            // 
            // transformButton
            // 
            this.transformButton.Location = new System.Drawing.Point(535, 268);
            this.transformButton.Margin = new System.Windows.Forms.Padding(4);
            this.transformButton.Name = "transformButton";
            this.transformButton.Size = new System.Drawing.Size(88, 28);
            this.transformButton.TabIndex = 5;
            this.transformButton.Text = "Transform";
            this.transformButton.UseVisualStyleBackColor = true;
            this.transformButton.Click += new System.EventHandler(this.transformButton_Click);
            // 
            // methodMenuStrip
            // 
            this.methodMenuStrip.BackColor = System.Drawing.SystemColors.Control;
            this.methodMenuStrip.ImageScalingSize = new System.Drawing.Size(20, 20);
            this.methodMenuStrip.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.addConstantMenuItem,
            this.multiplyByConstantMenuItem,
            this.toNegativeMenuItem,
            this.powerTransformMenuItem,
            this.logarithmicTransformMenuItem,
            this.adoptiveMenuItem,
            this.BernsenTransform,
            this.OtsuTransform,
            this.NiblackTransforms});
            this.methodMenuStrip.Name = "methodMenuStrip";
            this.methodMenuStrip.Size = new System.Drawing.Size(226, 220);
            // 
            // addConstantMenuItem
            // 
            this.addConstantMenuItem.Name = "addConstantMenuItem";
            this.addConstantMenuItem.Size = new System.Drawing.Size(225, 24);
            this.addConstantMenuItem.Text = "Add constant";
            this.addConstantMenuItem.Click += new System.EventHandler(this.addConstantMenuItem_Click);
            // 
            // multiplyByConstantMenuItem
            // 
            this.multiplyByConstantMenuItem.Name = "multiplyByConstantMenuItem";
            this.multiplyByConstantMenuItem.Size = new System.Drawing.Size(225, 24);
            this.multiplyByConstantMenuItem.Text = "Multiply by constant";
            this.multiplyByConstantMenuItem.Click += new System.EventHandler(this.multiplyByConstantMenuItem_Click);
            // 
            // toNegativeMenuItem
            // 
            this.toNegativeMenuItem.Name = "toNegativeMenuItem";
            this.toNegativeMenuItem.Size = new System.Drawing.Size(225, 24);
            this.toNegativeMenuItem.Text = "To negative";
            this.toNegativeMenuItem.Click += new System.EventHandler(this.toNegativeMenuItem_Click);
            // 
            // powerTransformMenuItem
            // 
            this.powerTransformMenuItem.Name = "powerTransformMenuItem";
            this.powerTransformMenuItem.Size = new System.Drawing.Size(225, 24);
            this.powerTransformMenuItem.Text = "Power transform";
            this.powerTransformMenuItem.Click += new System.EventHandler(this.powerTransformMenuItem_Click);
            // 
            // logarithmicTransformMenuItem
            // 
            this.logarithmicTransformMenuItem.Name = "logarithmicTransformMenuItem";
            this.logarithmicTransformMenuItem.Size = new System.Drawing.Size(225, 24);
            this.logarithmicTransformMenuItem.Text = "Logarithmic transform";
            this.logarithmicTransformMenuItem.Click += new System.EventHandler(this.logarithmicTransformMenuItem_Click);
            // 
            // adoptiveMenuItem
            // 
            this.adoptiveMenuItem.Name = "adoptiveMenuItem";
            this.adoptiveMenuItem.Size = new System.Drawing.Size(225, 24);
            this.adoptiveMenuItem.Text = "Adoptive transform";
            this.adoptiveMenuItem.Click += new System.EventHandler(this.adoptiveMenuItem_Click);
            // 
            // BernsenTransform
            // 
            this.BernsenTransform.Name = "BernsenTransform";
            this.BernsenTransform.Size = new System.Drawing.Size(225, 24);
            this.BernsenTransform.Text = "Bernsen Transform";
            this.BernsenTransform.Click += new System.EventHandler(this.BernsenTransform_Click);
            // 
            // OtsuTransform
            // 
            this.OtsuTransform.Name = "OtsuTransform";
            this.OtsuTransform.Size = new System.Drawing.Size(225, 24);
            this.OtsuTransform.Text = "By Otsu method";
            this.OtsuTransform.Click += new System.EventHandler(this.OtsuTransform_Click);
            // 
            // NiblackTransforms
            // 
            this.NiblackTransforms.Name = "NiblackTransforms";
            this.NiblackTransforms.Size = new System.Drawing.Size(225, 24);
            this.NiblackTransforms.Text = "Niblack Transform";
            this.NiblackTransforms.Click += new System.EventHandler(this.NiblackTransforms_Click);
            // 
            // paramsTextBox
            // 
            this.paramsTextBox.Location = new System.Drawing.Point(535, 236);
            this.paramsTextBox.Margin = new System.Windows.Forms.Padding(4);
            this.paramsTextBox.Name = "paramsTextBox";
            this.paramsTextBox.Size = new System.Drawing.Size(87, 22);
            this.paramsTextBox.TabIndex = 7;
            this.paramsTextBox.TextChanged += new System.EventHandler(this.paramsTextBox_TextChanged);
            // 
            // openSourceDialog
            // 
            this.openSourceDialog.FileName = "openSourceDialog";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(537, 215);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(85, 17);
            this.label3.TabIndex = 8;
            this.label3.Text = "Parameters:";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1159, 518);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.paramsTextBox);
            this.Controls.Add(this.transformButton);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.chooseButton);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.resultPictureBox);
            this.Controls.Add(this.sourcePictureBox);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Margin = new System.Windows.Forms.Padding(4);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "Form1";
            this.Text = "Image Filters";
            ((System.ComponentModel.ISupportInitialize)(this.sourcePictureBox)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.resultPictureBox)).EndInit();
            this.methodMenuStrip.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.PictureBox sourcePictureBox;
        private System.Windows.Forms.PictureBox resultPictureBox;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button chooseButton;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button transformButton;
        private System.Windows.Forms.ContextMenuStrip methodMenuStrip;
        private System.Windows.Forms.ToolStripMenuItem addConstantMenuItem;
        private System.Windows.Forms.ToolStripMenuItem multiplyByConstantMenuItem;
        private System.Windows.Forms.ToolStripMenuItem toNegativeMenuItem;
        private System.Windows.Forms.ToolStripMenuItem powerTransformMenuItem;
        private System.Windows.Forms.ToolStripMenuItem logarithmicTransformMenuItem;
        private System.Windows.Forms.TextBox paramsTextBox;
        private System.Windows.Forms.OpenFileDialog openSourceDialog;
        private System.Windows.Forms.ToolStripMenuItem adoptiveMenuItem;
        private System.Windows.Forms.ToolStripMenuItem BernsenTransform;
        private System.Windows.Forms.ToolStripMenuItem OtsuTransform;
        private System.Windows.Forms.ToolStripMenuItem NiblackTransforms;
        private System.Windows.Forms.Label label3;
        //private System.Windows.Forms.ToolStripMenuItem Otsu;
        //  private System.Windows.Forms.ToolStripMenuItem Bernsen;
    }
}

